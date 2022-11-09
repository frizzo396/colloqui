package com.accenture.interview.utils.mail;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The Class MailUtils.
 */
public class MailUtils {
	
	/**
	 * Instantiates a new mail utils.
	 */
	private MailUtils() {
		
	}
	
	/**
	 * Creates the availability subject.
	 *
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @return the string
	 */
	public static String createAvailabilitySubject(String candidateName, String candidateSurname) {
		return "Colloquio " + candidateName + " " + candidateSurname;
	}

	/**
	 * Creates the availability body response.
	 *
	 * @param interviewDate the interview date
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @return the string
	 */
	public static String createAvailabilityBodyResponse(Date interviewDate, String candidateName, String candidateSurname) {
		LocalDateTime localDate = interviewDate.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();

		String dateString = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

		return "Buongiorno,<br>"
		+ " il responsabile ha approvato il colloquio per il candidato <strong>" + candidateName + " " + candidateSurname +"</strong>"
		+ " in data <strong>" + dateString + "</strong>. <br>"
		+ "Le informazioni sul candidato sono disponibili sulla sezione <strong>In Progress</strong>.";
	}

}
