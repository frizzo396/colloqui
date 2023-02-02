package com.accenture.interview.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum MailTypeEnum.
 */
@AllArgsConstructor
@Getter
public enum MailTypeEnum
{

	/** The interview insert. */
	INTERVIEW_INSERT("mail.body.interview.insert"),

	/** The availability insert. */
	AVAILABILITY_INSERT("mail.body.availability.insert"),

	/** The availability approve. */
	AVAILABILITY_APPROVE("mail.body.availability.approve"),
	
	AVAILABILITY_REFUSE("mail.body.availability.refuse"),

	/** The feedback insert. */
	FEEDBACK_INSERT("mail.body.feeback.insert"),

	/** The user register. */
	USER_REGISTER("mail.body.user.register"),

	/** The user welcome. */
	USER_WELCOME("mail.body.user.welcome"),
	
	/** The recover user password. */
	USER_RECOVER_PASSWORD("mail.body.user.recover-pwd");	

	/** The value. */
	private String value;
}
