package com.accenture.interview.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.to.interview.InsertAvailabilityTO;

/**
 * The Class AvailabilityFacade.
 */
@Component
public class AvailabilityFacade {
	
	/** The availability service. */
	@Autowired
	private AvailabilityService availabilityService;
		
	
	/**
	 * Adds the availabilty interview.
	 *
	 * @param insertAvailabilityTO the insert availability TO
	 */
	public Long addAvailabiltyInterview(InsertAvailabilityTO insertAvailabilityTO) {
		availabilityService.addAvailabiltyInterview(insertAvailabilityTO);
		return insertAvailabilityTO.getInterviewId();
	}
	
	

}
