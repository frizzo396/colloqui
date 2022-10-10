package com.accenture.interview.rto.feedback;

import com.accenture.interview.to.feedback.CreateTechFeedbackTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CreateTechFeedbackRTO.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateTechFeedbackRTO {

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

	/** The comment. */
	private String comment;

	/**
	 * Instantiates a new creates the tech feedback response.
	 *
	 * @param createTechFeedbackRequest the create tech feedback
	 *                                  request
	 */
	public CreateTechFeedbackRTO(CreateTechFeedbackTO createTechFeedbackRequest) {
		this.java = createTechFeedbackRequest.getJava();
		this.sqlVoto = createTechFeedbackRequest.getSqlVoto();
		this.htmlCss = createTechFeedbackRequest.getHtmlCss();
		this.angular = createTechFeedbackRequest.getAngular();
		this.spring = createTechFeedbackRequest.getSpring();
		this.other = createTechFeedbackRequest.getOther();
		this.comment = createTechFeedbackRequest.getComment();
	}

}
