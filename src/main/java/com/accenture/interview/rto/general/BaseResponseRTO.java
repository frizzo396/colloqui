package com.accenture.interview.rto.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class BaseResponseRTO.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseResponseRTO {

	/** The body. */
	private Object body;

	/** The error. */
	private String error;

	/**
	 * Instantiates a new base response RTO.
	 *
	 * @param body the body
	 */
	public BaseResponseRTO(Object body) {
		this.body = body;
		this.error = null;
	}

	/**
	 * Instantiates a new base response RTO.
	 *
	 * @param error the error
	 */
	public BaseResponseRTO(String error) {
		this.error = error;
		this.body = null;
	}

}
