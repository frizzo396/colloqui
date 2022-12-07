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

	/** The comment. */
	private String comment;

	/**
	 * Instantiates a new creates the tech feedback response.
	 *
	 * @param createTechFeedbackRequest the create tech feedback
	 *                                  request
	 */
	public CreateTechFeedbackRTO(CreateTechFeedbackTO createTechFeedbackRequest) {
		this.comment = createTechFeedbackRequest.getComment();
	}

}
