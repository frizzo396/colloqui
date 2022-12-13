package com.accenture.interview.rto.feedback;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class TechnicalFeedbackRTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalFeedbackRTO {
	
	/** The id. */
	private long id;

	/** The comment. */
	private String comment;
	
	/** The scores. */
	private String scores;

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this, TechnicalFeedbackRTO.class);

	}
}
