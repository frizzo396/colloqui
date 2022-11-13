package com.accenture.interview.utils.mail;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.utils.constants.WebPaths;

/**
 * The Class MailUtils.
 */
@Component
public class MailUtils {

	/** The message source. */
	@Autowired
	private MessageSource messageSource;


	/**
	 * Creates the availability subject.
	 *
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @return the string
	 */
	public String createInterviewSubject(String candidateName, String candidateSurname) {		
		return messageSource.getMessage("mail.subject.interview", null, Locale.getDefault())
				.replace("$1", candidateName)
				.replace("$2", candidateSurname);
	}

	/**
	 * Creates the registration subject.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the string
	 */
	public String createRegistrationSubject(String enterpriseId) {
		return messageSource.getMessage("mail.subject.registration", null, Locale.getDefault())
				.replace("$1", enterpriseId);
	}
	
	/**
	 * Creates the registration welcome subject.
	 *
	 * @return the string
	 */
	public String createRegistrationWelcomeSubject() {
		return messageSource.getMessage("mail.subject.registration.success", null, Locale.getDefault());
	}
	
	/**
	 * Creates the registration welcome body.
	 *
	 * @return the string
	 */
	public String createRegistrationWelcomeBody() {		
		return messageSource.getMessage("mail.body.user.register.success", null, Locale.getDefault())
				.replace("$link", WebPaths.HTTP + WebPaths.HOST + WebPaths.BASE_PATH + WebPaths.HOME);
	}

	/**
	 * Creates the availability body response.
	 *
	 * @param interviewDate the interview date
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @return the string
	 */
	public String createApproveAvailabilityBody(Date interviewDate, String candidateName, String candidateSurname) {
		return messageSource.getMessage("mail.body.availability.approve", null, Locale.getDefault())
				.replace("$1", candidateName)
				.replace("$2", candidateSurname)
				.replace("$3", formatDateToString(interviewDate))
				.replace("$link", WebPaths.HTTP + WebPaths.HOST + WebPaths.BASE_PATH + WebPaths.IN_PROGRESS);
	}


	/**
	 * Creates the insert availability body response.
	 *
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @param avTO the av TO
	 * @return the string
	 */
	public String createInsertAvailabilityBody(String candidateName, String candidateSurname, InsertAvailabilityTO avTO) {
		return messageSource.getMessage("mail.body.availability.insert", null, Locale.getDefault())
				.replace("$1", candidateName)
				.replace("$2", candidateSurname)
				.replace("$3", formatDateToString(avTO.getFirstDate()))
				.replace("$4", formatDateToString(avTO.getSecondDate()))
				.replace("$5", formatDateToString(avTO.getThirdDate()))
				.replace("$link", WebPaths.HTTP + WebPaths.HOST + WebPaths.BASE_PATH + WebPaths.ASSIGNED);
	}

	/**
	 * Creates the insert feedback body response.
	 *
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @return the string
	 */
	public String createInsertFeedbackBody(String candidateName, String candidateSurname) {				 
		return messageSource.getMessage("mail.body.feeback.insert", null, Locale.getDefault())
				.replace("$1", candidateName)
				.replace("$2", candidateSurname)
				.replace("$link", WebPaths.HTTP + WebPaths.HOST + WebPaths.BASE_PATH + WebPaths.ASSIGNED);

	}


	/**
	 * Creates the insert interview body response.
	 *
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @return the string
	 */
	public String createInsertInterviewBody(String candidateName, String candidateSurname) {		
		return messageSource.getMessage("mail.body.interview.insert", null, Locale.getDefault())
				.replace("$1", candidateName)
				.replace("$2", candidateSurname)
				.replace("$link", WebPaths.HTTP + WebPaths.HOST + WebPaths.BASE_PATH + WebPaths.IN_PROGRESS);
	}


	/**
	 * Creates the register new user body.
	 *
	 * @param enterpriseId the enterprise id
	 * @param mail the mail
	 * @return the string
	 */
	public String createRegisterNewUserBody(String enterpriseId, String mail) {		
		return messageSource.getMessage("mail.body.user.register", null, Locale.getDefault())
				.replace("$1", enterpriseId)
				.replace("$2", mail);
	}


	/**
	 * Mail not send.
	 *
	 * @return the string
	 */
	public String mailNotSend() {
		return messageSource.getMessage("mail.notsend", null, Locale.getDefault());
	}
	
	/**
	 * Registration request mail not send.
	 *
	 * @return the string
	 */
	public String registrationRequestMailNotSend() {
		return messageSource.getMessage("interviewer.req.registration.mail.notsend", null, Locale.getDefault());
	}


	/**
	 * Format date to string.
	 *
	 * @param date the date
	 * @return the string
	 */
	private String formatDateToString(Date date) {
		LocalDateTime localDate = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();

		return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}




}
