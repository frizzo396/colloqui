package com.accenture.interview.to.interview;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class ApproveAvailabilityTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApproveAvailabilityTO {
	
	/** The interview id. */
	@NotNull(message = "interview.error.not-found")
	private Long interviewId;
	
	/** The approved date. */
   private String approvedDate;

   private String newDate;

}
