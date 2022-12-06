package com.accenture.interview.to.feedback;

import javax.validation.constraints.Max;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScoreTechFeedbackTO {
	
	private String technology;
	
	@Max(value = 10, message = "feedback.error.max-score.value")
	private int score;
	
	public ScoreTechFeedbackTO(String name, int s) {
		this.technology = name;
		this.score = s;
	}
}
