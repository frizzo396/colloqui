package com.accenture.interview.rto.interview;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class InProgressInterviewRTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InProgressInterviewRTO {
	
	/** The id. */
	private long id;
		
	/** The candidate name. */
	private String candidateName;

	/** The candidate surname. */
	private String candidateSurname;
	
	/** The candidate level. */
	private String candidateLevel;
	
	/** The interview type. */
	private Long interviewType;

	/** The scheduled date. */
	private Date scheduledDate;
	
	/** The site. */
	private String site;
	
}
