package com.accenture.interview.to.interviewer;

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
public class AccessUserTO {
	
	/** The enterprise id. */
	@NotEmpty(message = "access.user.name.not-empty")
	private String enterpriseId;
	
	/** The password. */
	@NotEmpty(message = "access.user.password.not-empty")
	private String password;

}
