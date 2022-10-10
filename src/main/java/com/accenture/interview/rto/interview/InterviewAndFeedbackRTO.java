package com.accenture.interview.rto.interview;

import java.util.Date;

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

	/** The interview type. */
	private long interviewType;

	/** The scheduled date. */
	private Date scheduledDate;

	/** The final feedback. */
	private String finalFeedback;

	/** The technical feedback. */
	private TechnicalFeedbackRTO technicalFeedback;

	/** The motivational feedback. */
	private MotivationalFeedbackRTO motivationalFeedback;

	/**
	 * Instantiates a new interview and feedback RTO.
	 *
	 * @param idColloquio      the id colloquio
	 * @param candidateName    the candidate name
	 * @param candidateSurname the candidate surname
	 * @param interviewType    the interview type
	 * @param scheduledDate    the scheduled date
	 * @param feedback         the feedback
	 */
	public InterviewAndFeedbackRTO(long idColloquio, String candidateName, String candidateSurname, long interviewType, Date scheduledDate, String feedback) {
		this.idColloquio = idColloquio;
		this.candidateName = candidateName;
		this.candidateSurname = candidateSurname;
		this.interviewType = interviewType;
		this.scheduledDate = scheduledDate;
		this.finalFeedback = feedback;
		this.technicalFeedback = null;
		this.motivationalFeedback = null;
	}

}
