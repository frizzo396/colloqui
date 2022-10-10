package com.accenture.interview.rto.general;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class StartEndDateRTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StartEndDateRTO {

	/** The start date. */
	private Date startDate;

	/** The end date. */
	private Date endDate;

}
