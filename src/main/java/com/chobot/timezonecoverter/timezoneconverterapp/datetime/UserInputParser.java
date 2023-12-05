package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.tuple.Pair;

/**
 * The `UserInputParser` class provides functionality to parse user input
 * strings and extract a zoned date-time along with the target time-zone.
 * The input is expected to be in the format: [date-time w/ time-zone] to [target time-zone].
 * For example: "2010-02-04T14:39:28 PST to EST".
 *
 * @author chobot
 * @version 1.0
 */
public class UserInputParser {
	
	private static final String TO_SEPARATOR = "to";
	
	public static Pair<ZonedDateTime, String> parse(String input) {
		return parseInput(input);
	}
	
	/**
	 * Splits the user input to a zoned date-time and target time-zone pair
	 * 
	 * @param input the user input string in format: [date-time w/ time-zone] to [target time-zone]
	 * 		  <p>
	 * 		  i.e. "2010-02-04T14:39:28 PST to EST"
	 * @return a pair with the date-time w/ time-zone as a {@code ZonedDateTime} and the target time-zone as a String, not null
	 * @throws IllegalArgumentException   
	 * 		   if the input does not follow the format
	 */
	private static Pair<ZonedDateTime, String> parseInput(String input) {

		validateInput(input);
		
		String[] textSplit = splitInputBySeparator(input);
		validateInputFormat(textSplit);
		
		String dateTimeString = extractDateTimeString(textSplit);
		String targetTimeZoneString = extractTargetTimeZoneString(textSplit);
		
		ZonedDateTime zonedDateTime = parseDateTime(dateTimeString);
		return Pair.of(zonedDateTime, targetTimeZoneString);
	}
		
	/**
	 * Validates the user input to ensure it follows the expected format.
	 *
	 * @param input The user input string.
	 * @throws IllegalArgumentException 
	 *         If the input is null, empty, or does not contain the TO_SEPARATOR.
	 */
	private static void validateInput(String input) {
		
		if (input == null  	|| 
			input.isEmpty() ||
			!input.contains(TO_SEPARATOR)
		) {	
			handleInvalidUserInputFormat();
		}
	}
	
	/**
	 * Validates the format of the input split array.
	 *
	 * @param textSplit The array resulting from splitting the input.
	 * @throws IllegalArgumentException 
	 *         If the format doesn't follow the expected array length of 2
	 */
	private static void validateInputFormat(String[] textSplit) {
		if (textSplit.length != 2) {
			handleInvalidUserInputFormat();
		}
	}
	
	/**
	 * Splits the input string by the TO_SEPARATOR.
	 * <p>
	 * The TO_SEPARATOR ensures proper handling even if the user includes
	 * variations of the string "to" to their input, preventing potential 
	 * issues with unexpected strings
	 *
	 * @param input The input string.
	 * @return An array of strings resulting from the split.
	 */
	private static String[] splitInputBySeparator(String input) {
		return input.split(TO_SEPARATOR);
	}
	
	/**
	 * Extracts the date-time string from the split array.
	 *
	 * @param textSplit The array resulting from splitting the input.
	 * @return The date-time string.
	 */
	private static String extractDateTimeString(String[] textSplit) {
		return textSplit[0].trim();
	}
	
	/**
	 * Extracts the target time-zone string from the split array, converting it to uppercase.
	 *
	 * @param textSplit The array resulting from splitting the input.
	 * @return The target time-zone string in uppercase.
	 */
	private static String extractTargetTimeZoneString(String[] textSplit) {
		return textSplit[1].trim().toUpperCase();
	}
	
	/**
	 * Parses the date-time string using the ZonedDateTimeStringParser.
	 *
	 * @param dateTimeString The date-time string to parse.
	 * @return A ZonedDateTime object parsed from the input string.
	 * @throws IllegalArgumentException
	 *         if the format of the date time is invalid i.e. could not be parsed
	 */
	private static ZonedDateTime parseDateTime(String dateTimeString) {
		try {
			return ZonedDateTimeStringParser.parse(dateTimeString);
		}
		catch (DateTimeParseException e) {
			throw handleInvalidDateTimeFormat(dateTimeString, e);
		}
	}
	
	/**
	 * Handles the case where the user input format is invalid.
	 * Throws an IllegalArgumentException with a descriptive error message.
	 */
	private static IllegalArgumentException handleInvalidUserInputFormat() throws IllegalArgumentException {
		throw new IllegalArgumentException("Input must follow the format: [date-time w/ time-zone] to [target time-zone]");
	}
	
	/**
	 * Handles the case where the date-time string cannot be parsed.
	 * Throws an IllegalArgumentException with a descriptive error message.
	 *
	 * @param e the exception indicating the parsing failure
	 */
	private static IllegalArgumentException handleInvalidDateTimeFormat(String dateTimeString, DateTimeParseException e) throws IllegalArgumentException {
		throw new IllegalArgumentException("The format of the date time is invalid: " + dateTimeString, e);
	}
}
