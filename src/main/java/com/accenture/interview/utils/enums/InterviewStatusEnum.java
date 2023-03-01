package com.accenture.interview.utils.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.accenture.interview.to.interview.InterviewStatusTO;

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

   /** The rescheduled. */
   RESCHEDULED(6, "Rescheduled"),

   /** The canceled. */
   CANCELED(7, "Canceled");
	
   /** The value. */
	private Integer value;
	
   /** The description. */
	private String description;
	
	
	
   /**
    * Gets the interview status list.
    *
    * @return the interview status list
    */
   public static List<InterviewStatusTO> getInterviewStatusList() {
      return Arrays.asList(InterviewStatusEnum.values()).stream()
            .map(a -> new InterviewStatusTO(a.getValue(), a.getDescription()))
            .collect(Collectors.toList());
   }
}
