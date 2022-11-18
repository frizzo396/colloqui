package com.accenture.interview.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Availability;
import com.accenture.interview.entity.Interview;
import com.accenture.interview.repository.interview.AvailabilityRepository;
import com.accenture.interview.repository.interview.InterviewRepository;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.utils.enums.InterviewStatusEnum;

/**
 * The Class AvailabilityService.
 */
@Service
public class AvailabilityService {

	/** The availability repository. */
	@Autowired
	private AvailabilityRepository availabilityRepository;
	
	/** The interview repository. */
	@Autowired
	private InterviewRepository interviewRepository;


	/**
	 * Adds the availabilty interview.
	 *
	 * @param insertAvailabilityTO the insert availability TO
	 */
	public void addAvailabiltyInterview(InsertAvailabilityTO insertAvailabilityTO) {
		Optional<Interview> optInterview = interviewRepository.findInterviewById(insertAvailabilityTO.getInterviewId());		
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
			interview.setStatus(InterviewStatusEnum.IN_PROGRESS.getValue());
			List<Availability> availabilityList = createAvailabilityList(interview, insertAvailabilityTO);
			interviewRepository.save(interview);
			availabilityRepository.saveAll(availabilityList);			
		}
	} 
	
	/**
	 * Approve availabilty.
	 *
	 * @param approveAvailabilityTO the approve availability TO
	 */
	public void approveAvailabilty(ApproveAvailabilityTO approveAvailabilityTO) {
		Optional<Interview> optInterview = interviewRepository.findInterviewById(approveAvailabilityTO.getInterviewId());		
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
			interview.setStatus(InterviewStatusEnum.SCHEDULED.getValue());
			interview.setScheduledDate(approveAvailabilityTO.getApprovedDate());
			interviewRepository.save(interview);			
		}
	}
	
	/**
	 * Creates the availability list.
	 *
	 * @param interview the interview
	 * @param insertAvailabilityTO the insert availability TO
	 * @return the list
	 */
	private List<Availability> createAvailabilityList(Interview interview, InsertAvailabilityTO insertAvailabilityTO){
		List<Availability> availabilityList = new ArrayList<>();
		Availability firstAvailability = new Availability(interview, insertAvailabilityTO.getFirstDate());
		Availability secondAvailability = new Availability(interview, insertAvailabilityTO.getSecondDate());
		Availability thirdAvailability = new Availability(interview, insertAvailabilityTO.getThirdDate());
		availabilityList.add(firstAvailability);
		availabilityList.add(secondAvailability);
		availabilityList.add(thirdAvailability);		
		return availabilityList;	
	}

}
