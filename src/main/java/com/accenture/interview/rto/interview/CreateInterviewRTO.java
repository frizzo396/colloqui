package com.accenture.interview.rto.interview;

import java.util.Date;

import com.accenture.interview.to.interview.CreateInterviewTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CreateInterviewResponse.
 */
@Getter
@Setter

/**
 * Instantiates a new creates the interview RTO.
 */
@NoArgsConstructor
public class CreateInterviewRTO {

	/** The candidate name. */
	private String candidateName;

	/** The candidate surname. */
	private String candidateSurname;

	/** The candidate birth. */
	@JsonFormat(pattern = "MM-dd-YYYY HH:mm", timezone = "Europe/Rome")
	private Date candidateBirth;

	/** The mail. */
	private String mail;

	/** The edu qualification. */
	private String eduQualification;

	/** The candidate type. */
	private String candidateType;

	/** The interview type. */
	private String interviewType;

	/** The enterprise id. */
	private String enterpriseId;

	/** The scheduled date. */
	@JsonFormat(pattern = "MM-dd-YYYY HH:mm", timezone = "Europe/Rome")
	private Date scheduledDate;

	/**
	 * Instantiates a new creates the interview response.
	 *
	 * @param createInterviewRequest the create interview
	 *                               request
	 */
	public CreateInterviewRTO(CreateInterviewTO createInterviewRequest) {
		this.candidateName = createInterviewRequest.getCandidateName();
		this.candidateSurname = createInterviewRequest.getCandidateSurname();
		this.candidateBirth = createInterviewRequest.getCandidateBirth();
		this.mail = createInterviewRequest.getMail();
		this.eduQualification = createInterviewRequest.getEduQualification();
		this.candidateType = createInterviewRequest.getCandidateType();
		this.interviewType = createInterviewRequest.getInterviewType();
		this.enterpriseId = createInterviewRequest.getEnterpriseId();
	}

}
