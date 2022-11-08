package com.accenture.interview.to.interview;

import java.util.Date;

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
	@NotNull(message = "availability.interview.notfound")
	private Long interviewId;
	
	/** The approved date. */
	@NotNull(message = "availability.approve.date.notempty")
	private Date approvedDate;

}
