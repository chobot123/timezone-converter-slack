package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Converts this text from the slash command '/convert':
 * 	[Timezoned-date/time (ISO-8601 and common custom formatters)] to [target TimeZone i.e. PST, EST, etc]
 */
public class DateTimeProcessor {
	
	/**
	 * Parses user input zoned date-time string and converts the zoned date-time to the target time-zone
	 *
	 * @param input
	 * 		  the user input string in format: [date-time w/ time-zone] to [target time-zone]
	 * 		  <p>
	 * 		  i.e. "2010-02-04T14:39:28 PST to EST"
	 * @return the zoned date-time formatted by "MMM dd, yyyy h:mm a", or exception message
	 */
	public static String processInputDateTimeToTargetTimeZone(String input) {
		try {
			Pair<ZonedDateTime, String> parsedInput = UserInputParser.parseInput(input);
			ZonedDateTime targetZonedDateTime = DateTimeConverter.convert(parsedInput.getLeft(), parsedInput.getRight());
			return OutputDateTimeFormatter.formatZonedDateTimeToOutputString(targetZonedDateTime, input, parsedInput.getRight());
		}
		catch (Exception e) {
			return e.getMessage();
		}
	}
}
