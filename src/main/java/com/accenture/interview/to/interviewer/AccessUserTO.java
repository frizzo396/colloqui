package com.accenture.interview.to.interviewer;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessUserTO {
	
	@NotEmpty(message = "access.user.name.not-empty")
	private String enterpriseId;
	
	@NotEmpty(message = "access.user.password.not-empty")
	private String password;

}
