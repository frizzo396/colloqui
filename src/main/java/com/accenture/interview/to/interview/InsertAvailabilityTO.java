package com.accenture.interview.to.interview;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class InsertAvailabilityTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertAvailabilityTO {
	
	/** The interview id. */
	@NotNull(message = "availability.error.interview.not-found")
	private Long interviewId;
	
   /** The first date. */
   @NotEmpty(message = "availability.error.date.not-empty")
   private String firstDate;

   /** The second date. */
   @NotEmpty(message = "availability.error.date.not-empty")
   private String secondDate;

   /** The third date. */
   @NotEmpty(message = "availability.error.date.not-empty")
   private String thirdDate;

}
