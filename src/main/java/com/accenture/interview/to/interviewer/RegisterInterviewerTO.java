package com.accenture.interview.to.interviewer;

import javax.validation.constraints.Email;
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
public class RegisterInterviewerTO {	

	/** Responsible. */
	@NotNull(message = "interviewer.responsible.notnull")
	private Long isResponsible;

	/** The mail. */
	@Email(message = "interviewer.mail.invalid")
	@NotEmpty(message = "interviewer.mail.notempty")
	private String mail;

	/** The enterprise id. */
	@NotEmpty(message = "interviewer.enterpriseid.notempty")
	private String enterpriseId;

}
