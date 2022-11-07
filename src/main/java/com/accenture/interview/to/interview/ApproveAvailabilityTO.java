package com.accenture.interview.to.interview;

import java.util.Date;

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
	private Long interviewId;
	
	/** The approved date. */
	private Date approvedDate;

}
