package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The `OutputDateTimeFormatter` class provides functionality to format a {@code ZonedDateTime} into a specific output string.
 * <p>
 * The class uses a predefined date-time pattern to format the converted date-time string. The pattern format is
 * "MMM dd, yyyy h:mm a". For example, "Nov 30, 2023 02:45 PM".
 * <p>
 * Additionally, it includes a specific formatter for the Slack app output. The `formatZonedDateTimeToOutputString` method
 * takes a parsed date-time, initial user input, and target time-zone, and returns a formatted date-time string suitable
 * for Slack app output.
 *
 * Usage Example:
 * {@code
 * ZonedDateTime zonedDateTime = ZonedDateTime.now();
 * String input = "2023-12-04T10:30 PST to EST";
 * String targetTimeZone = "EST";
 * String formattedOutput = OutputDateTimeFormatter.formatZonedDateTimeToOutputString(zonedDateTime, input, targetTimeZone);
 * System.out.println("Formatted Output: " + formattedOutput);
 * }
 *
 * Date-Time Pattern:
 * The date-time pattern used by the class is "MMM dd, yyyy h:mm a".
 *
 * @see DateTimeFormatter
 * @author chobot
 * @version 1.0
 */
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
