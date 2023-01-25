package com.accenture.interview.to.interviewer;

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
public class ChangePasswordInterviewerTO {	

	/** The enterprise id. */
	private String enterpriseId;
	
	/** The id (primary key). */
	private long id;
	
	/** The old password. */
	@NotEmpty(message = "interviewer.change-pwd.oldPassword.not-empty")
	private String oldPassword;	
	
	/** The new password. */
	@NotEmpty(message = "interviewer.change-pwd.newPassword.not-empty")
	private String newPassword;
}
