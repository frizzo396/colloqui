package com.accenture.interview.utils.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.accenture.interview.rto.interview.InterviewTypeRTO;

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
	public Optional<Long> getIdFromDescription(String description) {
		return Arrays.stream(InterviewTypeEnum.values())
				.filter(interviewTypeEnum -> interviewTypeEnum.description.equals(description))
				.map(InterviewTypeEnum::getId)
				.findFirst();
	}

   /**
    * Gets the interview type list.
    *
    * @return the interview type list
    */
   public static List<InterviewTypeRTO> getInterviewTypeList() {
      return Arrays.stream(InterviewTypeEnum.values())
            .map(a -> new InterviewTypeRTO(a.getId(), a.getDescription()))
            .collect(Collectors.toList());
   }
}
