package com.accenture.interview.to.feedback;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CreateTechFeedbackRequest.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateTechFeedbackTO {

	/** The java. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int java;

	/** The sql voto. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int sqlVoto;

	/** The html css. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int htmlCss;

	/** The angular. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int angular;

	/** The spring. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int spring;

	/** The other. */
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int other;

	/** The final feedback. */
	@NotEmpty(message = "feedback.final.notempty")
	private String finalFeedback;

	/** The comment. */
	@NotEmpty(message = "feedback.comment.notempty")
	private String comment;

}
