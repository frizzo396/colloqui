package com.accenture.interview.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum Months.
 */
@Getter

/**
 * Instantiates a new months.
 *
 * @param number the number
 * @param value  the value
 */

/**
 * Instantiates a new months.
 *
 * @param number the number
 * @param value  the value
 */
@AllArgsConstructor
public enum Months
{

	/** The january. */
	JANUARY(1, "January"),

	/** The february. */
	FEBRUARY(2, "February"),

	/** The march. */
	MARCH(3, "March"),

	/** The april. */
	APRIL(4, "April"),

	/** The may. */
	MAY(5, "May"),

	/** The june. */
	JUNE(6, "June"),

	/** The july. */
	JULY(7, "July"),

	/** The august. */
	AUGUST(8, "August"),

	/** The september. */
	SEPTEMBER(9, "September"),

	/** The october. */
	OCTOBER(10, "October"),

	/** The november. */
	NOVEMBER(11, "November"),

	/** The december. */
	DECEMBER(12, "December");

	/** The number. */
	private Integer number;

	/** The value. */
	private String value;

	/**
	 * Gets the month.
	 *
	 * @param month the month
	 * @return the month
	 */
	public static Months getMonth(Integer month) {
		for (Months m : Months.values()) {
			if (m.getNumber().equals(month)) {
				return m;
			}
		}
		return null;
	}
}
