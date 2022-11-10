package com.accenture.interview.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum InterviewStatusEnum.
 */

/**
 * Gets the value.
 *
 * @return the value
 */
@Getter

/**
 * Instantiates a new interview status enum.
 *
 * @param value the value
 */
@AllArgsConstructor
public enum InterviewStatusEnum
{	
	
	/** The new. */
	NEW(1),
	
	/** The in progress. */
	IN_PROGRESS(2),
	

	/** The scheduled. */
	SCHEDULED(3),
		
	/** The completed. */
	COMPLETED(4);
	
	/** The status. */
	private Integer value;
}
