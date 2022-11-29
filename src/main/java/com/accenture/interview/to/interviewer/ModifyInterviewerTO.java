package com.accenture.interview.to.interviewer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CreateInterviewRequest.
 */
@Getter
@Setter
@NoArgsConstructor
public class ModifyInterviewerTO {	

	/** Responsible. */
	@NotNull(message = "interviewer.error.responsible.not-null")
	private Long isResponsible;

	/** The enterprise id. */
	@NotEmpty(message = "interviewer.error.enterpriseid.not-empty")
	private String enterpriseId;
	
	/** The status. */
	private long status;

}
