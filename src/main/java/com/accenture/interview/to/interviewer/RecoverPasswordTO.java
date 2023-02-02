package com.accenture.interview.to.interviewer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class AccessUserTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPasswordTO {
	
	/** The enterprise id. */
	@NotEmpty(message = "interviewer.error.enterpriseid.not-empty")
	private String enterpriseId;

	
	/** The mail. For recover password. */
	@Email(message = "interviewer.error.mail.invalid")
	@NotEmpty(message = "interviewer.error.mail.not-empty")	
	private String mail;
	
	private String password;

}
