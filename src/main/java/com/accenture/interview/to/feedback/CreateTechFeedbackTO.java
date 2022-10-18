package com.accenture.interview.to.feedback;

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
	private int java;

	/** The sql voto. */
	private int sqlVoto;

	/** The html css. */
	private int htmlCss;

	/** The angular. */
	private int angular;

	/** The spring. */
	private int spring;

	/** The other. */
	private int other;

	/** The final feedback. */
	@NotEmpty(message = "feedback.final.notempty")
	private String finalFeedback;

	/** The comment. */
	@NotEmpty(message = "feedback.comment.notempty")
	private String comment;

}
