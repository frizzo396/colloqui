package com.accenture.interview.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.service.EmailService;
import com.accenture.interview.service.InterviewService;
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
	private EmailService mailService;
	
		
	
	/**
	 * Adds the availabilty interview.
	 *
	 * @param insertAvailabilityTO the insert availability TO
	 * @return the long
	 */
	public Long addAvailabiltyInterview(InsertAvailabilityTO insertAvailabilityTO) {
		availabilityService.addAvailabiltyInterview(insertAvailabilityTO);
		return insertAvailabilityTO.getInterviewId();
	}
	
	/**
	 * Approve availability.
	 *
	 * @param approveAvailabilityTO the approve availability TO
	 * @return the long
	 */
	public Long approveAvailability(ApproveAvailabilityTO approveAvailabilityTO) {
		InterviewRTO interview = interviewService.findInterviewForApproval(approveAvailabilityTO.getInterviewId());
		if(!ObjectUtils.isEmpty(interview)) {
			availabilityService.approveAvailabilty(approveAvailabilityTO);
		}
		//Send mail to interviewer
		mailService.sendMail(interview.getInterviewerMail(), 
				interview.getCandidateMail(),
				interview.getAssignerMail(), 
				MailUtils.createAvailabilitySubject(interview.getCandidateName(), interview.getCandidateSurname()), 
				MailUtils.createAvailabilityBodyResponse(approveAvailabilityTO.getApprovedDate(), interview.getCandidateName(), interview.getCandidateSurname()));
		
		//Sent teams meeting to candidate
		// eventService.sendTeamsInvitation(approveAvailabilityTO.getScheduledDate(),
					// interview.getCandidateMail(), interview.getApprovedDate(),
					// interview.getCandidateSurname());
		
		return approveAvailabilityTO.getInterviewId();
	}
	
	

	
	

}
