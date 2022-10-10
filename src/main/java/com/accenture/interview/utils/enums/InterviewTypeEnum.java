package com.accenture.interview.utils.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum InterviewTypeEnum.
 */
@Getter
@AllArgsConstructor
public enum InterviewTypeEnum
{

	/** The motivazionale. */
	MOTIVAZIONALE(1, "MOTIVAZIONALE"),

	/** The tecnico. */
	TECNICO(2, "TECNICO");

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
	public Optional<Long> getIdFromDescription(String description) {
		return Arrays.stream(InterviewTypeEnum.values())
				.filter(interviewTypeEnum -> interviewTypeEnum.description.equals(description))
				.map(InterviewTypeEnum::getId)
				.findFirst();
	}
}
