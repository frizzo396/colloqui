package com.accenture.interview.to.interview;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class SearchInterviewTO.
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchInterviewTO {

	/** The candidate name. */
	private String candidateName;

	/** The candidate surname. */
	private String candidateSurname;

	/** The interview type. */
   private Long interviewType;

	/** The first date. */
	private Date firstDate;

	/** The second date. */
	private Date secondDate;

	/** The enterprise id. */
	private String enterpriseId;
	
	/** The candidate type. */
	private String candidateType;
	
	/** The site. */
   private Long site;

}
