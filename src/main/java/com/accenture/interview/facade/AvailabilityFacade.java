package com.accenture.interview.facade;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.service.general.MailService;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.to.interview.ReassignInterviewTO;
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

	/** The interviewer service. */
	@Autowired
	private InterviewerService interviewerService;

	/** The mail service. */
	@Autowired
	private MailService mailService;

	/**
	 * Adds the availabilty interview.
	 *
	 * @param insertAvailabilityTO the insert availability TO
	 * @return the base response RTO
	 */
	public BaseResponseRTO addAvailabiltyInterview(InsertAvailabilityTO insertAvailabilityTO) {
		String errorMsg = null;
		InterviewRTO interview = interviewService.findInterviewWithMailParams(insertAvailabilityTO.getInterviewId());		
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
	 * @return the base response RTO
	 */
	public BaseResponseRTO approveAvailability(ApproveAvailabilityTO approveAvailabilityTO) {
		String errorMsg = null;
		InterviewRTO interview = interviewService.findInterviewWithMailParams(approveAvailabilityTO.getInterviewId());
		if(!ObjectUtils.isEmpty(interview)) {
			availabilityService.approveAvailabilty(approveAvailabilityTO);

			MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()), 
					Arrays.asList(interview.getAssignerMail()), 
					Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname(), DateUtils.formatDateToString(approveAvailabilityTO.getApprovedDate())), 
					Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), 
					WebPaths.IN_PROGRESS);
			boolean mailResult = mailService.sendMail(mailParams, MailTypeEnum.AVAILABILITY_APPROVE);	

			/*String webLink = eventService.createTeamsMeeting(approveAvailabilityTO.getApprovedDate());
			String body = messageSource.getMessage("teams.event.body", null, Locale.getDefault()).replace("$link", webLink);
			String candidate = interview.getCandidateName() + " " + interview.getCandidateSurname();
			mailService.sendCalendarMail(new CalendarTO.Builder()
		                    .withSubject("Colloquio " + candidate)
		                    .withFrom(interview.getInterviewerMail())
		                    .withCc(interview.getAssignerMail())
		                    .withBody(body)
		                    .withToEmail(interview.getCandidateMail())
		                    .withMeetingStartTime(approveAvailabilityTO.getApprovedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
		                    .withMeetingEndTime(approveAvailabilityTO.getApprovedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusHours(1))
		                    .build());
			
			if(ObjectUtils.isEmpty(webLink)) {
				errorMsg = eventService.eventNotSendErrorMessage();
			}*/
			if(!mailResult) {
				errorMsg = mailService.mailNotSend();
			}
		}
		return new BaseResponseRTO(approveAvailabilityTO.getInterviewId(), errorMsg);
	}


	/**
	 * Refuse availability.
	 *
	 * @param interviewId the interview id
	 * @return the base response RTO
	 */
	public BaseResponseRTO refuseAvailability(Long interviewId) {
		String errorMsg = null;
		InterviewRTO interview = interviewService.findInterviewWithMailParams(interviewId);		
		Long refuseId = interviewService.refuseInterview(interviewId);

		MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()), 
				Arrays.asList(interview.getAssignerMail()), 
				Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), 
				Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), 
				WebPaths.ASSIGNED);
		boolean result = mailService.sendMail(mailParams, MailTypeEnum.AVAILABILITY_REFUSE);	

		if(!result) {
			errorMsg = mailService.mailNotSend();
		}
		return new BaseResponseRTO(refuseId, errorMsg);
	}

	/**
	 * Reassign availability.
	 *
	 * @param reassignTO the reassign TO
	 * @return the base response RTO
	 */
	public BaseResponseRTO reassignAvailability(ReassignInterviewTO reassignTO) {
		String errorMsg = null;
		InterviewerRTO newInterviewer = interviewerService.findInterviewerByEnterpriseId(reassignTO.getEnterpriseId());
		InterviewRTO interview = interviewService.findInterviewWithMailParams(reassignTO.getInterviewId());	
		Long reassignedInterview = interviewService.reassignInterview(reassignTO.getInterviewId(), newInterviewer);
		MailParametersTO mailParams = new MailParametersTO(Arrays.asList(newInterviewer.getMail()), 
				Arrays.asList(interview.getAssignerMail()), 
				Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), 
				Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), WebPaths.IN_PROGRESS);
		boolean result = mailService.sendMail(mailParams, MailTypeEnum.INTERVIEW_INSERT);	
		if(!result) {
			errorMsg = mailService.mailNotSend();
		}
		return new BaseResponseRTO(reassignedInterview, errorMsg);
	}

}
