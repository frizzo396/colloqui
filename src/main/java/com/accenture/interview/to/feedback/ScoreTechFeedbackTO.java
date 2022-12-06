package com.accenture.interview.to.feedback;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScoreTechFeedbackTO {
	
	private String technology;	
	private int score;
	
	public ScoreTechFeedbackTO(String name, int s) {
		this.technology = name;
		this.score = s;
	}
}
