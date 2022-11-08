package com.accenture.interview.rto.interview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class InterviewRTO.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InterviewRTO {
		
	/** The id. */
	private Long id;
	
	/** The site. */
	private String site;
	
	/** The candidate name. */
	private String candidateName;

	/** The candidate surname. */
	private String candidateSurname;

	/** The mail. */
	private String candidateMail;

	/** The interviewer mail. */
	private String interviewerMail;

	/** The assigner mail. */
	private String assignerMail;

}
