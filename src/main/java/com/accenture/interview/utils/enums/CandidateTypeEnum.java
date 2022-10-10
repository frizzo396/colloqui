package com.accenture.interview.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum CandidateTypeEnum.
 */
@Getter
@AllArgsConstructor
public enum CandidateTypeEnum
{

	/** The subco. */
	SUBCO(1, "SUBCO"),

	/** The junior. */
	JUNIOR(2, "JUNIOR"),

	/** The expert. */
	EXPERT(3, "EXPERT");

	/** The id. */
	private final long id;

	/** The description. */
	private final String description;

}
