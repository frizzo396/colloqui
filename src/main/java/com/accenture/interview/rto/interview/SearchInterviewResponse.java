package com.accenture.interview.rto.interview;

import java.util.Date;

import com.accenture.interview.entity.Interview;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class SearchInterviewResponse.
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchInterviewResponse {

	/** The id colloquio. */
	private long idColloquio;

	/** The candidate name. */
	private String candidateName;

	/** The candidate surname. */
	private String candidateSurname;

	/** The email. */
	private String email;

	/** The scheduled date. */
	@JsonFormat(pattern = "MM-dd-YYYY HH:mm", timezone = "Europe/Rome")
	private Date scheduledDate;

	/** The interview type. */
	private long interviewType;

	/** The enterprise id. */
	private String enterpriseId;

	/** The final feedback. */
	private String finalFeedback;

	/**
	 * Instantiates a new search interview response.
	 *
	 * @param interview the interview
	 */
	public SearchInterviewResponse(Interview interview) {
		this.idColloquio = interview.getId();
		this.candidateName = interview.getCandidateName();
		this.candidateSurname = interview.getCandidateSurname();
		this.email = interview.getMail();
		this.scheduledDate = interview.getScheduledDate();
		this.interviewType = interview.getInterviewType();
		this.enterpriseId = interview.getInterviewerId().getEnterpriseId();
		this.finalFeedback = interview.getFinalFeedback();
	}

}
