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
	
	private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a z");

	
	public static String convertInputToFormattedDateTimeString (String input) {
		try {
			Pair<ZonedDateTime, String> parsedInput = parseInput(input);
			ZonedDateTime targetZonedDateTime = convertToTargetTimeZone(parsedInput.getLeft(), parsedInput.getRight());
			return formatZonedDateTimeToOutputString(targetZonedDateTime, input);
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
	
	private static ZonedDateTime convertToTargetTimeZone(ZonedDateTime dateTime, String targetTimeZoneString) {
		ZoneId targetTimeZone = ZoneId.of(targetTimeZoneString);
		return dateTime.withZoneSameInstant(targetTimeZone);
	}
	
	private static String formatZonedDateTimeToOutputString(ZonedDateTime zonedDateTime, String input) {
		String formattedZonedDateTime = zonedDateTime.format(DATETIME_FORMAT);
		return input + " is *" + formattedZonedDateTime + "*.";
	}
}