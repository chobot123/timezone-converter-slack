package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Converts this text from the slash command '/convert':
 * 	[Timezoned-date/time (ISO-8601 and common custom formatters)] to [target TimeZone i.e. PST, EST, etc]
 */
public class UserInputDateTimeConverter {
	
	/**
	 * The date-time pattern to output the converted date time string as:
	 * <p>
	 * The pattern format is "MMM dd, yyyy h:mm a".
	 * For example, "Nov 30, 2023 02:45 PM"
	 */
	private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");
	
	/**
	 * Parses user input zoned date-time string and converts the zoned date-time to the target time-zone
	 *
	 * @param input
	 * 		  the user input string in format: [date-time w/ time-zone] to [target time-zone]
	 * 		  <p>
	 * 		  i.e. "2010-02-04T14:39:28 PST to EST"
	 * @return the zoned date-time formatted by "MMM dd, yyyy h:mm a"
	 * @throws DateTimeParseException
	 * 		   if the date time string format is invalid
	 * @throws DateTimeException
	 * 		   if the zone ID format is invalid
	 */
	public static String convertInputToFormattedDateTimeString (String input) {
		try {
			Pair<ZonedDateTime, String> parsedInput = parseInput(input);
			ZonedDateTime targetZonedDateTime = convertToTargetTimeZone(parsedInput.getLeft(), parsedInput.getRight());
			return formatZonedDateTimeToOutputString(targetZonedDateTime, input, parsedInput.getRight());
		}
		catch (DateTimeParseException e) {
			return "Invalid date/time format. Please provide a valid date/time.";
		}
		catch (DateTimeException e) {
			return "Invalid zone ID format. Please provide a valid zone ID.";
		}
		catch (IllegalArgumentException e) {
			return "Invalid input. Please provide a valid date/time and timezone.";
		}
	}
	
	/**
	 * Splits the user input to a zoned date-time and target time-zone pair
	 * 
	 * @param input the user input string in format: [date-time w/ time-zone] to [target time-zone]
	 * 		  <p>
	 * 		  i.e. "2010-02-04T14:39:28 PST to EST"
	 * @return a pair with the date-time w/ time-zone as a {@code ZonedDateTime} and the target time-zone as a String, not null
	 * @throws IllegalArgumentException
	 * 		   The user input does not follow the format
	 */
	private static Pair<ZonedDateTime, String> parseInput(String input) {
		
		String[] textSplit = input.split("to");
		if (textSplit.length < 2) {
			throw new IllegalArgumentException("");
		}
		
		String dateTimeString = textSplit[0].trim();
		String targetTimeZoneString = textSplit[1].trim().toUpperCase();
		ZonedDateTime zonedDateTime = ZonedDateTimeStringParser.parse(dateTimeString);
		return Pair.of(zonedDateTime, targetTimeZoneString);
	}
	
	/**
	 * Converts the {@code ZonedDateTime} to the same instant with the target time-zone (ZoneID or by ZoneId.SHORT_IDS)
	 * 
	 * @param zonedDateTime 
	 * 		  the zoned date-time object to convert, not null
	 * @param targetTimeZoneString
	 * 		  the target time-zone, not null
     * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
     * @throws DateTimeException if the zone id is not found
	 */
	private static ZonedDateTime convertToTargetTimeZone(ZonedDateTime zonedDateTime, String targetTimeZoneString) {
		ZoneId targetTimeZone = getZoneId(targetTimeZoneString);
		
		if (targetTimeZone != null) {
			return convertToTargetTimeZone(zonedDateTime, targetTimeZone);
		} else {
			return convertToAlternativeTimeZone(zonedDateTime, targetTimeZoneString);
		}
	}
	
	/**
	 * Converts the date-time to the same instant as the target time-zone (Zone ID)
	 * @param zonedDateTime 
	 *        the zoned date-time object to convert, not null
	 * @param targetTimeZone
	 * 		  the target time-zone, not null
	 * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
	 * @throws DateTimeException if the zone id is not found
	 */
	private static ZonedDateTime convertToTargetTimeZone(ZonedDateTime zonedDateTime, ZoneId targetTimeZone) {
		return zonedDateTime.withZoneSameInstant(targetTimeZone);
	}
	
	/**
	 * Converts the zoned date-time to the same instant as the target time zone (ZoneId.SHORT_IDS)
	 * <p>
	 * 
	 * This is for when the target time zone is a string, primarily for short_ids i.e. "EST", "PST", etc.
	 * @Overload {@code convertToTargetTimeZone(ZonedDateTime zonedDateTime, ZoneId targetTimeZone)}
	 * <p>
	 * @param zonedDateTime 
	 *        the zoned date-time object to convert, not null
	 * @param targetTimeZoneString
	 * 		  the target time-zone as a string, not null
	 * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
	 * @throws DateTimeException if the zone id is not found
	 */
	private static ZonedDateTime convertToAlternativeTimeZone(ZonedDateTime zonedDateTime, String targetTimeZoneString) {
				
		try {
			ZoneId alternativeTimeZone = getZoneId(ZoneId.SHORT_IDS.get(targetTimeZoneString));
			return convertToTargetTimeZone(zonedDateTime, alternativeTimeZone);
		}
		catch (NullPointerException e) {
			throw new DateTimeException("");
		}
	}
	
	/**
	 * Converts a time zone string as a {@code ZoneId}
	 * @param timeZoneString
	 * 		  time zone string, not null (usually in short form i.e. "EST", "PST")
	 * @return ZoneId, or null
	 */
	private static ZoneId getZoneId(String timeZoneString) {
		try {
			return ZoneId.of(timeZoneString);
		}
		catch (DateTimeException e) {
			return null;
		}
	}
	
	/**
	 * The user input formatter 
	 * @param zonedDateTime
	 * 		  parsed date-time
	 * @param input
	 *		  initial user input
	 * @param targetTimeZone
	 *        the target time-zone as a string
	 * @return the formatted date-time
	 */
	private static String formatZonedDateTimeToOutputString(ZonedDateTime zonedDateTime, String input, String targetTimeZone) {
		return String.format("%s is *%s %s*.", input, zonedDateTime.format(DATETIME_FORMAT), targetTimeZone);
	}
}