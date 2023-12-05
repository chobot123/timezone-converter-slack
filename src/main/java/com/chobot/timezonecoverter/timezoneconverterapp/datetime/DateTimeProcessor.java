package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.ZonedDateTime;

import org.apache.commons.lang3.tuple.Pair;

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
 * Usage Example:
 * {@code
 * String userInput = "2010-02-04T14:39:28 PST to EST";
 * String processedResult = DateTimeProcessor.processDateTimeInput(userInput);
 * System.out.println("Processed Result: " + processedResult);
 * }
 *
 * Error Handling:
 * In case of parsing or conversion errors, the method returns an error message.
 *
 * @author chobot
 * @version 1.0
 */
public class DateTimeProcessor {
	
    /**
     * Processes the user input for zoned date-time conversion.
     *
     * @param input The input string containing the zoned date-time and target time-zone.
     * @return The formatted result after parsing, converting, and formatting.
     *         In case of errors, an error message is returned.
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
