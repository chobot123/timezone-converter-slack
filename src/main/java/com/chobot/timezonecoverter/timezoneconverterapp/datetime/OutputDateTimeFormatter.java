package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class OutputDateTimeFormatter {
	/**
	 * The date-time pattern to output the converted date time string as:
	 * <p>
	 * The pattern format is "MMM dd, yyyy h:mm a".
	 * For example, "Nov 30, 2023 02:45 PM"
	 */
	private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");

	/**
	 * The slack app output formatter
	 * @param zonedDateTime
	 * 		  parsed date-time
	 * @param input
	 *		  initial user input
	 * @param targetTimeZone
	 *        the target time-zone as a string
	 * @return the formatted date-time
	 */
	public static String formatZonedDateTimeToOutputString(ZonedDateTime zonedDateTime, String input, String targetTimeZone) {
		return String.format("%s is *%s %s*.", input, zonedDateTime.format(DATETIME_FORMAT), targetTimeZone);
	}
}
