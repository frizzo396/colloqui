package com.accenture.interview.controller.base;

/**
 * The Class BaseController.
 */
public abstract class BaseController {

	/** The interview id. */
	protected static Integer interviewId;

	/**
	 * Update interview id.
	 *
	 * @param value the value
	 */
	protected static void updateInterviewId(Integer value) {
		interviewId = value;
	}

}
