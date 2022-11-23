package com.accenture.interview.to.feedback;

import javax.validation.constraints.Max;
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
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int standing;

	/** The school background. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int schoolBackground;

	/** The motivation. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int motivation;

	/** The soft skills. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int softSkills;

	/** The english level. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int englishLevel;

	/** The logic question. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int logicQuestion;

	/** The tech question. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int techQuestion;

	/** The final feedback. */
	@NotEmpty(message = "feedback.error.final.not-empty")
	private String finalFeedback;

	/** The comment. */
	@NotEmpty(message = "feedback.error.comment.not-empty")
	private String comment;

}
