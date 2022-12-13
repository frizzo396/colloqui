package com.accenture.interview.to.feedback;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CreateTechFeedbackRequest.
 */
@Getter
@Setter
public class CreateTechFeedbackTO {
	
	@Valid
	private List<ScoreTechFeedbackTO> techList;

	/** The final feedback. */
	@NotEmpty(message = "feedback.error.final.not-empty")
	private String finalFeedback;

	/** The comment. */
	@NotEmpty(message = "feedback.error.comment.not-empty")
	private String comment;	
	
	// NUOVO CAMPO PER TRASFORMAZIONE JSON
	private String scores;
	
	public CreateTechFeedbackTO() {		
		techList = new ArrayList<ScoreTechFeedbackTO>();
		techList.add(new ScoreTechFeedbackTO("", ""));
		techList.add(new ScoreTechFeedbackTO("", ""));
		techList.add(new ScoreTechFeedbackTO("", ""));
		techList.add(new ScoreTechFeedbackTO("", ""));
		techList.add(new ScoreTechFeedbackTO("", ""));
		techList.add(new ScoreTechFeedbackTO("", ""));
	}

}
