package com.accenture.interview.to.interview;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class InsertAvailabilityTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertAvailabilityTO {
	
	/** The interview id. */
	@NotNull(message = "availability.interview.notfound")
	private Long interviewId;
	
	/** The first date. */
	@NotNull(message = "availability.date.notempty")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date firstDate;
	
	/** The second date. */
	@NotNull(message = "availability.date.notempty")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date secondDate;
	
	/** The third date. */
	@NotNull(message = "availability.date.notempty")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date thirdDate;

}
