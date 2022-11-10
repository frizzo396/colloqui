package com.accenture.interview.utils.mail;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;

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
	public static String createInterviewSubject(String candidateName, String candidateSurname) {
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
	public static String createApproveAvailabilityBody(Date interviewDate, String candidateName, String candidateSurname) {
		String dateString = formatDateToString(interviewDate);
		return "Buongiorno,<br>"
		+ " il responsabile ha approvato il colloquio per il candidato <strong>" + candidateName + " " + candidateSurname +"</strong>"
		+ " in data <strong>" + dateString + "</strong>. <br>"
		+ "Le informazioni sul candidato sono disponibili sulla sezione <strong>In Progress</strong>.";
	}
	
	
	/**
	 * Creates the insert availability body response.
	 *
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @param avTO the av TO
	 * @return the string
	 */
	public static String createInsertAvailabilityBody(String candidateName, String candidateSurname, InsertAvailabilityTO avTO) {
		String firstDate = formatDateToString(avTO.getFirstDate());
		String secondDate = formatDateToString(avTO.getFirstDate());
		String thirdDate = formatDateToString(avTO.getFirstDate());
		
		return "Buongiorno,<br>"
				+ " l'intervistatore ha inserito le disponibilità per il colloquio del candidato <strong>" + candidateName + " " + candidateSurname +"</strong>"
				+ " nelle date (<strong>" + firstDate + "</strong>, <strong>" + secondDate + "</strong>, <strong>" + thirdDate +"</strong>). <br>"
				+ "Puoi procedere con l'approvazione nella sezione <strong>Assigned</strong>.";
		
	}
	
	/**
	 * Creates the insert feedback body response.
	 *
	 * @param interviewRTO the interview RTO
	 * @return the string
	 */
	public static String createInsertFeedbackBody(InterviewRTO interviewRTO) {		
		return "Buongiorno,<br>"
				+ " l'intervistatore ha inserito le valutazioni per il colloquio del candidato <strong>" + interviewRTO.getCandidateName() + " " + interviewRTO.getCandidateSurname() +"</strong>. <br>"
				+ "Puoi consultare le valutazioni nella sezione <strong>Assigned</strong>.";
		
	}
	

	/**
	 * Creates the insert interview body response.
	 *
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @return the string
	 */
	public static String createInsertInterviewBody(String candidateName, String candidateSurname) {		
		return "Buongiorno,<br>"
				+ " il responsabile ti ha assegnato il colloquio del candidato <strong>" + candidateName + " " + candidateSurname +"</strong>. <br>"
				+ "Puoi consultare il CV e le skills nella sezione <strong>In progress</strong>.";
		
	}
	
	
	/**
	 * Format date to string.
	 *
	 * @param date the date
	 * @return the string
	 */
	private static String formatDateToString(Date date) {
		LocalDateTime localDate = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();

		return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

}
