package com.accenture.interview.utils.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.accenture.interview.rto.general.StartEndDateRTO;
import com.accenture.interview.rto.interview.InterviewMonthRTO;
import com.accenture.interview.utils.enums.Months;

/**
 * The Class DateUtils.
 */
public class DateUtils {
	
	
	/**
	 * Instantiates a new date utils.
	 */
	private DateUtils() {
		
	}
	
	/**
	 * Calculate date intervals.
	 *
	 * @return the start end date RTO
	 */
	public static StartEndDateRTO calculateMonthDateIntervals() {
		LocalDate todaydate = LocalDate.now();
		LocalDate startDate = todaydate.withDayOfMonth(1);
		LocalDate endDate = todaydate.with(TemporalAdjusters.lastDayOfMonth());
		return new StartEndDateRTO(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}

	/**
	 * Calculate year date intervals.
	 *
	 * @return the start end date RTO
	 */
	public static StartEndDateRTO calculateYearDateIntervals() {
		LocalDate now = LocalDate.now();
		LocalDate startDate = now.with(TemporalAdjusters.firstDayOfYear());
		LocalDate endDate = now.with(TemporalAdjusters.lastDayOfYear());
		return new StartEndDateRTO(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}

	/**
	 * Creates the empty interview month list.
	 *
	 * @return the list
	 */
	public static List<InterviewMonthRTO> createEmptyInterviewMonthList() {
		List<InterviewMonthRTO> result = new ArrayList<>();
		for (int i = 1; i < 13; i++) {
			result.add(new InterviewMonthRTO(i, Months.getMonth(i).getValue(), 0));
		}
		return result;
	}
	
	/**
	 * Format date to string.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String formatDateToString(Date date) {
		LocalDateTime localDate = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();

      return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}

}
