package com.accenture.interview.to.feedback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CreateTechFeedbackRequest.
 */
@Getter
@Setter
public class CreateTechFeedbackTO {
		
	private Map<String,ScoreTechFeedbackTO> techMap;
	
	private List<ScoreTechFeedbackTO> techList;

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
	
	// NUOVI CAMPI TESTUALI NELLA FORM - START
	private String tech_1;
	private String tech_2;
	private String tech_3;
	private String tech_4;
	private String tech_5;
	private String tech_6;
	// NUOVI CAMPI TESTUALI NELLA FORM - END
	
	public CreateTechFeedbackTO() {
		techMap = new HashMap<String,ScoreTechFeedbackTO>();
		techMap.put("tech_1", new ScoreTechFeedbackTO("", 0));
		techMap.put("tech_2", new ScoreTechFeedbackTO("", 0));
		techMap.put("tech_3", new ScoreTechFeedbackTO("", 0));
		techMap.put("tech_4", new ScoreTechFeedbackTO("", 0));
		techMap.put("tech_5", new ScoreTechFeedbackTO("", 0));
		techMap.put("tech_6", new ScoreTechFeedbackTO("", 0));
		
		techList = new ArrayList<ScoreTechFeedbackTO>();
		techList.add(new ScoreTechFeedbackTO("", 0));
		techList.add(new ScoreTechFeedbackTO("", 0));
		techList.add(new ScoreTechFeedbackTO("", 0));
		techList.add(new ScoreTechFeedbackTO("", 0));
		techList.add(new ScoreTechFeedbackTO("", 0));
		techList.add(new ScoreTechFeedbackTO("", 0));
	}

}
