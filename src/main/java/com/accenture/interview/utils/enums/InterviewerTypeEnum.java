package com.accenture.interview.utils.enums;

import java.util.Arrays;
import java.util.Optional;

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
	
	/**
	 * Gets the id from description.
	 *
	 * @param description the description
	 * @return the id from description
	 */
	public static Long getIdFromDescription(String description) {
		
		Optional<Long> optId = Arrays.stream(InterviewerTypeEnum.values())
		.filter(interviewerTypeEnum -> interviewerTypeEnum.description.equals(description))
		.map(InterviewerTypeEnum::getId)
		.findFirst();
		
		if (optId.isPresent() ) {
			return optId.get();
		}
		return null;
	}	

}
