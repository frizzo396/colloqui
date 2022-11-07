package com.accenture.interview.rto.interview;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class ApproveDateRTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateListRTO {

	/** The availability dates. */
	private List<Date> availabilityDates;
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this, DateListRTO.class);

	}

}
