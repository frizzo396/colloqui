package com.accenture.interview.to.interview;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
	private String interviewType;

	/** The first date. */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date firstDate;

	/** The second date. */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date secondDate;

	/** The enterprise id. */
	private String enterpriseId;
	
	/** The candidate type. */
	private String candidateType;
	
	/** The site. */
	private String site;

}
