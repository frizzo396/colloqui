package com.accenture.interview.utils.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.accenture.interview.rto.BaseTypeRTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum CandidateTypeEnum.
 */

/**
 * Gets the description.
 *
 * @return the description
 */
@Getter

/**
 * Instantiates a new candidate type enum.
 *
 * @param id          the id
 * @param description the description
 */
@AllArgsConstructor
public enum CandidateTypeEnum
{

	/** The subco. */
	SUBCO(1, "Subco"),

	/** The junior. */
	JUNIOR(2, "Junior"),

   /** The senior. */
	SENIOR(3, "Senior");

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
      return Arrays.stream(CandidateTypeEnum.values())
            .filter(interviewTypeEnum -> interviewTypeEnum.description.equals(description))
            .map(CandidateTypeEnum::getId)
            .findFirst().orElse(null);
   }

   /**
    * Gets the type list.
    *
    * @return the type list
    */
   public static List<BaseTypeRTO> getTypeList() {
      return Arrays.stream(CandidateTypeEnum.values())
            .map(a -> new BaseTypeRTO(a.getId(), a.getDescription()))
            .collect(Collectors.toList());
   }

}
