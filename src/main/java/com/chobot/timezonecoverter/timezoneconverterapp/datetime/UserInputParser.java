package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import org.apache.commons.lang3.tuple.Pair;

public class UserInputParser {
	
	private static final String TO_SEPARATOR = "to";
	
	/**
	 * Splits the user input to a zoned date-time and target time-zone pair
	 * 
	 * @param input the user input string in format: [date-time w/ time-zone] to [target time-zone]
	 * 		  <p>
	 * 		  i.e. "2010-02-04T14:39:28 PST to EST"
	 * @return a pair with the date-time w/ time-zone as a {@code ZonedDateTime} and the target time-zone as a String, not null
	 * @throws NullPointerException
	 * 		   if the input is null or empty
	 * @throws IllegalArgumentException   
	 * 		   if the input does not follow the format
	 */
	public static Pair<ZonedDateTime, String> parseInput(String input) {
		Objects.requireNonNull(input, "Input cannot be null.");
		
		String[] textSplit = input.split(TO_SEPARATOR);
		if (input.isEmpty() || textSplit.length < 2) {
			handleInvalidUserInputFormat();
		}
		
		String dateTimeString = textSplit[0].trim();
		String targetTimeZoneString = textSplit[1].trim().toUpperCase();
		
		try {
			ZonedDateTime zonedDateTime = ZonedDateTimeStringParser.parse(dateTimeString);
			return Pair.of(zonedDateTime, targetTimeZoneString);
		}
		catch (DateTimeParseException e) {
			handleInvalidDateTimeFormat(e);
		}
		
        throw new IllegalArgumentException("Failed to parse input.");
	}
	
	/**
	 * Handles the case where the user input format is invalid.
	 * Throws an IllegalArgumentException with a descriptive error message.
	 */
	private static void handleInvalidUserInputFormat() {
		throw new IllegalArgumentException("Input must follow the format: [date-time w/ time-zone] to [target time-zone].");
	}
	
	/**
	 * Handles the case where the date-time string cannot be parsed.
	 * Throws an IllegalArgumentException with a descriptive error message.
	 *
	 * @param e the exception indicating the parsing failure
	 */
	private static IllegalArgumentException handleInvalidDateTimeFormat(DateTimeParseException e) throws IllegalArgumentException {
		throw new IllegalArgumentException("Invalid date-time format: " + e.getMessage(), e);
	}
}
