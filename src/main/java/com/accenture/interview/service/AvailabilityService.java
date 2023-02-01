package com.accenture.interview.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Availability;
import com.accenture.interview.entity.Interview;
import com.accenture.interview.repository.interview.AvailabilityRepository;
import com.accenture.interview.repository.interview.InterviewRepository;
import com.accenture.interview.rto.interview.DateListRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
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
    * @param firstDate            the first date
    * @param secondDate           the second date
    * @param thirdDate            the third date
    */
   public void addAvailabiltyInterview(InsertAvailabilityTO insertAvailabilityTO, Date firstDate, Date secondDate, Date thirdDate) {
		Optional<Interview> optInterview = interviewRepository.findInterviewById(insertAvailabilityTO.getInterviewId());		
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
         interview.setUpdatedDate(new Date());
			interview.setStatus(InterviewStatusEnum.IN_PROGRESS.getValue());
         List<Availability> availabilityList = createAvailabilityList(interview, firstDate, secondDate, thirdDate);
			interviewRepository.save(interview);
			availabilityRepository.saveAll(availabilityList);			
		}
	} 
	
	/**
	 * Approve availabilty.
	 *
	 * @param approveAvailabilityTO the approve availability TO
	 */
   public void approveAvailabilty(Long interviewId, Date approvedDate, String newDate, boolean isReschedule) {
      Optional<Interview> optInterview = interviewRepository.findInterviewById(interviewId);
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
         interview.setUpdatedDate(new Date());
         if (!isReschedule) {
            interview.setStatus(InterviewStatusEnum.SCHEDULED.getValue());
            interview.setScheduledDate(approvedDate);
         } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime newLocalDate = LocalDateTime.parse(newDate, formatter);
            interview.setStatus(InterviewStatusEnum.RESCHEDULED.getValue());
            interview.setScheduledDate(Date.from(newLocalDate.atZone(ZoneId.systemDefault()).toInstant()));
         }
			interviewRepository.save(interview);			
		}
	}
	
	/**
    * Creates the availability list.
    *
    * @param interview  the interview
    * @param firstDate  the first date
    * @param secondDate the second date
    * @param thirdDate  the third date
    * @return the list
    */
   private List<Availability> createAvailabilityList(Interview interview, Date firstDate, Date secondDate, Date thirdDate) {
		List<Availability> availabilityList = new ArrayList<>();
      Availability firstAvailability = new Availability(interview, firstDate);
      Availability secondAvailability = new Availability(interview, secondDate);
      Availability thirdAvailability = new Availability(interview, thirdDate);
      availabilityList.add(firstAvailability);
      availabilityList.add(secondAvailability);
      availabilityList.add(thirdAvailability);
		return availabilityList;	
	}

   /**
    * Adds the availability dates.
    *
    * @param interviews the interviews
    * @return the list
    */
   public List<InterviewAndFeedbackRTO> addAvailabilityDates(List<InterviewAndFeedbackRTO> interviews) {
      for (InterviewAndFeedbackRTO interview : interviews) {
         List<Date> availabilities = availabilityRepository.findAvailabilityDatesByInterviewId(interview.getIdColloquio());
         interview.setAvailabilityDates(new DateListRTO(availabilities));
      }
      return interviews;
   }

}
