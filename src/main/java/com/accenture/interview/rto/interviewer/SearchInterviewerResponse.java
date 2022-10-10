package com.accenture.interview.rto.interviewer;

import com.accenture.interview.entity.Interviewer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class SearchInterviewerResponse.
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchInterviewerResponse {

	/** The id. */
	private long id;

	/** The enterprise id. */
	private String enterpriseId;

	/** The mail. */
	private String mail;

	/**
	 * Instantiates a new search interviewer response.
	 *
	 * @param interviewer the interviewer
	 */
	public SearchInterviewerResponse(Interviewer interviewer) {
		this.id = interviewer.getId();
		this.enterpriseId = interviewer.getEnterpriseId();
		this.mail = interviewer.getMail();
	}

}
