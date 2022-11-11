package com.accenture.interview.controller.base;

/**
 * The Class BaseController.
 */
public abstract class BaseController {

	/** The interview id. */
	protected static Long interviewId;

	/**
	 * Update interview id.
	 *
	 * @param value the value
	 */
	protected static void updateInterviewId(Long value) {
		interviewId = value;
	}

}
