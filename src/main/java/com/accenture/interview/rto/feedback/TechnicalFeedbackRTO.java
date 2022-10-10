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

	/** The java. */
	private long java;

	/** The sql. */
	private int sql;

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
