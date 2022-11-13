package com.accenture.interview.to.interviewer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class RequestRegistrationTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegistrationTO {
	
	/** The enterprise id. */
	private String enterpriseId;
	
	/** The mail. */
	@Email(message = "interviewer.mail.invalid")
	@NotEmpty(message = "interviewer.mail.notempty")
	private String mail;
}
