package com.accenture.interview.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * The Enum InterviewStatusEnum.
 */
@Getter
@AllArgsConstructor
public enum InterviewStatusEnum
{	
	
	/** The new. */
	NEW(1),
	
	/** The in progress. */
	IN_PROGRESS(2),
	
	/** The waiting date. */
	WAITING_DATE(3),
	
	/** The completed. */
	COMPLETED(4);
	
	/** The status. */
	private Integer value;
}
