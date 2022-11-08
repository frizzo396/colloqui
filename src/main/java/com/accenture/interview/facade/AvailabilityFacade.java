package com.accenture.interview.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;

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
		
		// eventService.sendTeamsInvitation(approveAvailabilityTO.getScheduledDate(),
					// interview.getCandidateMail(), interview.getApprovedDate(),
					// interview.getCandidateSurname());
		//Invio event teams
		return approveAvailabilityTO.getInterviewId();
	}
	
	

}
