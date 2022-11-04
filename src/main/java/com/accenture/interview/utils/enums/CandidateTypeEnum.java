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
	SUBCO(1, "Subco"),

	/** The junior. */
	JUNIOR(2, "Junior"),

	/** The expert. */
	SENIOR(3, "Senior");

	/** The id. */
	private final long id;

	/** The description. */
	private final String description;

}
