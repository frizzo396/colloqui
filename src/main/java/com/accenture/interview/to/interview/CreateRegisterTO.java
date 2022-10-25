package com.accenture.interview.to.interview;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CreateInterviewRequest.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateRegisterTO {	

	/** Responsible. */
	@NotEmpty(message = "user.responsible.notempty")
	private String isResponsible;

	/** The mail. */
	@Email(message = "user.mail.invalid")
	@NotEmpty(message = "user.mail.notempty")
	private String mail;

	/** The enterprise id. */
	@NotEmpty(message = "user.enterpriseid.notempty")
	private String enterpriseId;

}
