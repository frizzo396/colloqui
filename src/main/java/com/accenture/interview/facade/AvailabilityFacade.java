package com.accenture.interview.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.MailService;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.utils.mail.MailUtils;

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
	
	@Autowired
	private MailUtils mailUtils;

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
		//Send mail to interviewer
		boolean result = mailService.sendMail(interview.getInterviewerMail(), 
				interview.getInterviewerMail(),
				interview.getAssignerMail(), 
				mailUtils.createInterviewSubject(interview.getCandidateName(), interview.getCandidateSurname()), 
				mailUtils.createInsertAvailabilityBody(interview.getCandidateName(), interview.getCandidateSurname(), insertAvailabilityTO));
		
		if(!result) {
			errorMsg = mailUtils.mailNotSend();
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
		}
		//Send mail to interviewer
		boolean result = mailService.sendMail(interview.getInterviewerMail(), 
				interview.getInterviewerMail(),
				interview.getAssignerMail(), 
				mailUtils.createInterviewSubject(interview.getCandidateName(), interview.getCandidateSurname()), 
				mailUtils.createApproveAvailabilityBody(approveAvailabilityTO.getApprovedDate(), interview.getCandidateName(), interview.getCandidateSurname()));

		//Sent teams meeting to candidate
		// eventService.sendTeamsInvitation(approveAvailabilityTO.getScheduledDate(),
		// interview.getCandidateMail(), interview.getApprovedDate(),
		// interview.getCandidateSurname());

		if(!result) {
			errorMsg = mailUtils.mailNotSend();
		}	
		return new BaseResponseRTO(approveAvailabilityTO.getInterviewId(), errorMsg);
	}

}
