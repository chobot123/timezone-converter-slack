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
	
	private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");

	
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
			return e.getMessage();
		}
	}
	
	private static Pair<ZonedDateTime, String> parseInput(String input) {
		
		String[] textSplit = input.split("to");
		if (textSplit.length < 2) {
			throw new IllegalArgumentException("Invalid input. Please provide a valid date/time and timezone.");
		}
		
		String dateTimeString = textSplit[0].trim();
		String targetTimeZoneString = textSplit[1].trim();
		ZonedDateTime zonedDateTime = ZonedDateTimeStringParser.parse(dateTimeString);
		return Pair.of(zonedDateTime, targetTimeZoneString);
	}
	
	private static ZonedDateTime convertToTargetTimeZone(ZonedDateTime zonedDateTime, String targetTimeZoneString) {
		ZoneId targetTimeZone = getZoneId(targetTimeZoneString);
		
		if (targetTimeZone != null) {
			return convertToTargetTimeZone(zonedDateTime, targetTimeZone);
		} else {
			return convertToAlternativeTimeZone(zonedDateTime, targetTimeZoneString);
		}
	}
	
	private static ZonedDateTime convertToTargetTimeZone(ZonedDateTime zonedDateTime, ZoneId targetTimeZone) {
		return zonedDateTime.withZoneSameInstant(targetTimeZone);
	}
	
	private static ZonedDateTime convertToAlternativeTimeZone(ZonedDateTime zonedDateTime, String targetTimeZoneString) {
		ZoneId alternativeTimeZone = getZoneId(ZoneId.SHORT_IDS.get(targetTimeZoneString));
		
		if (alternativeTimeZone != null) {
			return convertToTargetTimeZone(zonedDateTime, alternativeTimeZone);
		} else {
			throw new IllegalArgumentException("Invalid time zone: " + targetTimeZoneString + ".");
		}
	}
	
	private static ZoneId getZoneId(String timeZoneString) {
		try {
			return ZoneId.of(timeZoneString);
		}
		catch (DateTimeException e) {
			return null;
		}
	}
	
	private static String formatZonedDateTimeToOutputString(ZonedDateTime zonedDateTime, String input, String targetTimeZone) {
		return String.format("%s is *%s %s*.", input, zonedDateTime.format(DATETIME_FORMAT), targetTimeZone);
	}
}