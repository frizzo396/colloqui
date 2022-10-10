package com.accenture.interview.rto.feedback;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class MotivationalFeedbackRTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MotivationalFeedbackRTO {

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

	/** The comment. */
	private String comment;

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this, MotivationalFeedbackRTO.class);

	}
}
