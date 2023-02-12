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

   /** The interview insert without notes. */
   INTERVIEW_INSERT_WITHOUT_NOTES("mail.body.interview.insert.without.notes"),

	/** The availability insert. */
	AVAILABILITY_INSERT("mail.body.availability.insert"),

	/** The availability approve. */
	AVAILABILITY_APPROVE("mail.body.availability.approve"),
	
   /** The availability refuse. */
	AVAILABILITY_REFUSE("mail.body.availability.refuse"),

	/** The feedback insert. */
	FEEDBACK_INSERT("mail.body.feeback.insert"),

	/** The user register. */
	USER_REGISTER("mail.body.user.register"),

	/** The user welcome. */
	USER_WELCOME("mail.body.user.welcome"),
	
   /** The user recover password. */
   USER_RECOVER_PASSWORD("mail.body.user.recover-pwd"),
	
   /** The availability reschedule. */
   AVAILABILITY_RESCHEDULE("mail.body.availability.reschedule"),

   /** The availability reschedule accepted. */
   AVAILABILITY_RESCHEDULE_ACCEPTED("mail.body.availability.reschedule.accepted"),

   /** The interview assigned. */
   INTERVIEW_ASSIGNED("mail.body.interview.assigned"),

   /** The interview assigned with notes. */
   INTERVIEW_ASSIGNED_WITH_NOTES("mail.body.interview.assigned.notes");


	/** The value. */
	private String value;
}
