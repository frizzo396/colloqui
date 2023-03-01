package com.accenture.interview.utils.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.accenture.interview.rto.BaseTypeRTO;

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
	MOTIVAZIONALE(1, "Motivazionale"),

	/** The tecnico. */
	TECNICO(2, "Tecnico");

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
		return Arrays.stream(InterviewTypeEnum.values())
				.filter(interviewTypeEnum -> interviewTypeEnum.description.equals(description))
				.map(InterviewTypeEnum::getId)
            .findFirst().orElse(null);
	}

   public static List<BaseTypeRTO> getTypeList() {
      return Arrays.stream(InterviewTypeEnum.values())
            .map(a -> new BaseTypeRTO(a.getId(), a.getDescription()))
            .collect(Collectors.toList());
   }
}
