package com.accenture.interview.facade;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.general.MailService;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.to.mail.MailParametersTO;
import com.accenture.interview.utils.constants.WebPaths;
import com.accenture.interview.utils.date.DateUtils;
import com.accenture.interview.utils.enums.MailTypeEnum;

/**
 * The Class AvailabilityFacade.
 */
@Component
public class AvailabilityFacade {

	/** The availability service. */
	@Autowired
	private AvailabilityService availabilityService;

	/** The interview service. */
	@Autowired
	private InterviewService interviewService;

	/** The mail service. */
	@Autowired
	private MailService mailService;


	/**
	 * Adds the availabilty interview.
	 *
	 * @param insertAvailabilityTO the insert availability TO
	 * @return the long
	 */
	public BaseResponseRTO addAvailabiltyInterview(InsertAvailabilityTO insertAvailabilityTO) {
		String errorMsg = null;
		InterviewRTO interview = interviewService.findInterviewForApproval(insertAvailabilityTO.getInterviewId());		
		availabilityService.addAvailabiltyInterview(insertAvailabilityTO);

		MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()), Arrays.asList(interview.getAssignerMail()), 
				Arrays.asList(interview.getCandidateName(), 
						interview.getCandidateSurname(),
						DateUtils.formatDateToString(insertAvailabilityTO.getFirstDate()),
						DateUtils.formatDateToString(insertAvailabilityTO.getSecondDate()),
						DateUtils.formatDateToString(insertAvailabilityTO.getThirdDate())), 
				Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), 
				WebPaths.ASSIGNED);
		boolean result = mailService.sendMail(mailParams, MailTypeEnum.AVAILABILITY_INSERT);		

		if(!result) {
			errorMsg = mailService.mailNotSend();
		}		
		return new BaseResponseRTO(insertAvailabilityTO.getInterviewId(), errorMsg);
	}

	/**
	 * Approve availability.
	 *
	 * @param approveAvailabilityTO the approve availability TO
	 * @return the long
	 */
	public BaseResponseRTO approveAvailability(ApproveAvailabilityTO approveAvailabilityTO) {
		String errorMsg = null;
		InterviewRTO interview = interviewService.findInterviewForApproval(approveAvailabilityTO.getInterviewId());
		if(!ObjectUtils.isEmpty(interview)) {
			availabilityService.approveAvailabilty(approveAvailabilityTO);

			MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()), 
					Arrays.asList(interview.getAssignerMail()), 
					Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname(), DateUtils.formatDateToString(approveAvailabilityTO.getApprovedDate())), 
					Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), 
					WebPaths.IN_PROGRESS);
			boolean result = mailService.sendMail(mailParams, MailTypeEnum.AVAILABILITY_APPROVE);	

			//TODO: Sent teams meeting to candidate
			if(!result) {
				errorMsg = mailService.mailNotSend();
			}
		}
		return new BaseResponseRTO(approveAvailabilityTO.getInterviewId(), errorMsg);
	}

}
