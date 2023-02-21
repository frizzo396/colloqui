package com.accenture.interview.rto.feedback;

import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CreateMotivationFeedbackRTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMotivationFeedbackRTO {

	/** The standing. */
	private int standing;

	/** The school background. */
	private int schoolBackground;

	/** The motivation. */
	private int motivation;

	/** The soft skills. */
	private int softSkills;

	/** The english level. */
	private int englishLevel;

	/** The logic question. */
	private int logicQuestion;

	/** The tech question. */
	private int techQuestion;

	/** The comment. */
	private String comment;

	/**
	 * Instantiates a new creates the motivation feedback
	 * response.
	 *
	 * @param createMotivationFeedbackRequest the create
	 *                                        motivation
	 *                                        feedback request
	 */
	public CreateMotivationFeedbackRTO(CreateMotivationFeedbackTO createMotivationFeedbackRequest) {
		this.standing = createMotivationFeedbackRequest.getStanding();
		this.schoolBackground = createMotivationFeedbackRequest.getSchoolBackground();
		this.motivation = createMotivationFeedbackRequest.getMotivation();
		this.softSkills = createMotivationFeedbackRequest.getSoftSkills();
		this.englishLevel = createMotivationFeedbackRequest.getEnglishLevel();
		this.logicQuestion = createMotivationFeedbackRequest.getLogicQuestion();
		this.techQuestion = createMotivationFeedbackRequest.getTechQuestion();
		this.comment = createMotivationFeedbackRequest.getComment();
	}

}
