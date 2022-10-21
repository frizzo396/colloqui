package com.accenture.interview.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum CandidateTypeEnum.
 */
@Getter
@AllArgsConstructor
public enum InterviewerTypeEnum
{

	/** The responsabile. */
	RESPONSABILE(1, "RESPONSABILE"),

	/** The utente normale. */
	NORMALE(2, "NORMALE");

	/** The id. */
	private final long id;

	/** The description. */
	private final String description;

}
