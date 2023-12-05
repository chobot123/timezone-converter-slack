package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.tuple.Pair;

public class DateTimeProcessor {
	
	/**
	 * The `DateTimeProcessor` class provides functionality to parse user input zoned date-time strings,
	 * convert the zoned date-time to the target time-zone, and format the result.
	 * <p>
	 * The input is expected to be in the format: [date-time w/ time-zone] to [target time-zone].
	 * For example: "2010-02-04T14:39:28 PST to EST".
	 * <p>
	 * The class utilizes the {@code UserInputParser} to parse the user input into a zoned date-time
	 * and target time-zone pair. It then uses the {@code DateTimeConverter} to convert the zoned date-time
	 * to the target time-zone. Finally, the result is formatted using the {@code OutputDateTimeFormatter}.
	 *
	 * @author chobot
	 * @version 1.0
	 */
	public static String processDateTimeInput(String input) {
		try {
			Pair<ZonedDateTime, String> parsedInput = UserInputParser.parse(input);
			ZonedDateTime targetZonedDateTime = DateTimeConverter.convert(parsedInput.getLeft(), parsedInput.getRight());
			return OutputDateTimeFormatter.formatZonedDateTimeToOutputString(targetZonedDateTime, input, parsedInput.getRight());
		}
		catch (Exception e) {
			return e.getMessage();
		}
	}
}
