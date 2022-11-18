package com.accenture.interview.to.feedback;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CreateMotivationFeedbackRequest.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateMotivationFeedbackTO {

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

	/** The final feedback. */
	@NotEmpty(message = "feedback.error.final.not-empty")
	private String finalFeedback;

	/** The comment. */
	@NotEmpty(message = "feedback.error.comment.not-empty")
	private String comment;

}
