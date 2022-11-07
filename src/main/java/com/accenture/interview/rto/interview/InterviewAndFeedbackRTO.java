package com.accenture.interview.rto.interview;

import java.util.Date;
import java.util.List;

import com.accenture.interview.rto.feedback.MotivationalFeedbackRTO;
import com.accenture.interview.rto.feedback.TechnicalFeedbackRTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class InterviewAndFeedbackRTO.
 */
@Getter
@Setter
@NoArgsConstructor
public class InterviewAndFeedbackRTO {

	/** The id colloquio. */
	private long idColloquio;

	/** The candidate name. */
	private String candidateName;

	/** The candidate surname. */
	private String candidateSurname;
	
	/** The candidate level. */
	private String candidateLevel;

	/** The interview type. */
	private long interviewType;

	/** The scheduled date. */
	private Date scheduledDate;
	
	/** The site. */
	private String site;
	
	/** The enterprise id. */
	private String enterpriseId;

	/** The final feedback. */
	private String finalFeedback;
	
	/** The status. */
	private Integer status;
	
	private DateListRTO availabilityDates;

	/** The technical feedback. */
	private TechnicalFeedbackRTO technicalFeedback;

	/** The motivational feedback. */
	private MotivationalFeedbackRTO motivationalFeedback;

	/**
	 * Instantiates a new interview and feedback RTO.
	 *
	 * @param idColloquio the id colloquio
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @param candidateLevel the candidate level
	 * @param interviewType the interview type
	 * @param scheduledDate the scheduled date
	 * @param site the site
	 * @param enterpriseId the enterprise id
	 * @param finalFeedback the final feedback
	 */
	public InterviewAndFeedbackRTO(long idColloquio, String candidateName, String candidateSurname, String candidateLevel, long interviewType, Date scheduledDate, String site, String enterpriseId,
			String finalFeedback, Integer status) {
		super();
		this.idColloquio = idColloquio;
		this.candidateName = candidateName;
		this.candidateSurname = candidateSurname;
		this.candidateLevel = candidateLevel;
		this.interviewType = interviewType;
		this.scheduledDate = scheduledDate;
		this.site = site;
		this.enterpriseId = enterpriseId;
		this.finalFeedback = finalFeedback;
		this.status = status;
	}
	
	public InterviewAndFeedbackRTO(long idColloquio, String candidateName, String candidateSurname, String candidateLevel, long interviewType, Date scheduledDate, String site, String enterpriseId,
			String finalFeedback) {
		super();
		this.idColloquio = idColloquio;
		this.candidateName = candidateName;
		this.candidateSurname = candidateSurname;
		this.candidateLevel = candidateLevel;
		this.interviewType = interviewType;
		this.scheduledDate = scheduledDate;
		this.site = site;
		this.enterpriseId = enterpriseId;
		this.finalFeedback = finalFeedback;
	}

	/**
	 * Instantiates a new interview and feedback RTO.
	 *
	 * @param idColloquio the id colloquio
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @param candidateLevel the candidate level
	 * @param interviewType the interview type
	 * @param scheduledDate the scheduled date
	 * @param site the site
	 * @param finalFeedback the final feedback
	 * @param status the status
	 */
	public InterviewAndFeedbackRTO(long idColloquio, String candidateName, String candidateSurname, String candidateLevel, 
			long interviewType, Date scheduledDate, String site, String finalFeedback, Integer status) {
		super();
		this.idColloquio = idColloquio;
		this.candidateName = candidateName;
		this.candidateSurname = candidateSurname;
		this.candidateLevel = candidateLevel;
		this.interviewType = interviewType;
		this.scheduledDate = scheduledDate;
		this.site = site;
		this.finalFeedback = finalFeedback;
		this.status = status;
	}
	
	/**
	 * Instantiates a new interview and feedback RTO.
	 *
	 * @param idColloquio the id colloquio
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @param candidateLevel the candidate level
	 * @param interviewType the interview type
	 * @param scheduledDate the scheduled date
	 * @param site the site
	 * @param finalFeedback the final feedback
	 */
	public InterviewAndFeedbackRTO(long idColloquio, String candidateName, String candidateSurname, String candidateLevel, 
			long interviewType, Date scheduledDate, String site, String finalFeedback) {
		super();
		this.idColloquio = idColloquio;
		this.candidateName = candidateName;
		this.candidateSurname = candidateSurname;
		this.candidateLevel = candidateLevel;
		this.interviewType = interviewType;
		this.scheduledDate = scheduledDate;
		this.site = site;
		this.finalFeedback = finalFeedback;
	}
	
	




	
	

}
