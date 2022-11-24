package com.accenture.interview.to.interview;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class ReassignInterviewTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReassignInterviewTO {
	
	/** The interview id. */
	@NotNull(message = "availability.error.refuse.interview.empty")
	private Long interviewId;
	
	/** The enterprise id. */
	@NotEmpty(message = "interview.error.enterpriseid.not-empty")
	private String enterpriseId;

}
