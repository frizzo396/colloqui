package com.accenture.interview.utils.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.accenture.interview.rto.interview.InterviewStatusRTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum InterviewStatusEnum.
 */
@Getter
@AllArgsConstructor
public enum InterviewStatusEnum
{	
	
	/** The new. */
   NEW(1, "New"),
	
	/** The in progress. */
   IN_PROGRESS(2, "In progress"),
	
	/** The scheduled. */
   SCHEDULED(3, "Scheduled"),
		
	/** The completed. */
   COMPLETED(4, "Completed"),
	
	/** The refused. */
   REFUSED(5, "Refused"),

   /** The refused. */
   RESCHEDULED(6, "Rescheduled");
	
   /** The value. */
	private Integer value;
	
   /** The description. */
	private String description;
	
	
	
   /**
    * Gets the interview status list.
    *
    * @return the interview status list
    */
   public static List<InterviewStatusRTO> getInterviewStatusList() {
      return Arrays.asList(InterviewStatusEnum.values()).stream()
            .map(a -> new InterviewStatusRTO(a.getValue(), a.getDescription()))
            .collect(Collectors.toList());
   }
}
