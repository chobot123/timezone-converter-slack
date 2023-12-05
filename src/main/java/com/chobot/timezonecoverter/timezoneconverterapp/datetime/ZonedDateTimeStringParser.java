package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * The `ZonedDateTimeStringParser` class provides functionality to parse zoned date-time strings.
 * It supports both ISO-8601 and custom date-time formats commonly used in user input.
 * If the input string cannot be parsed using any of the specified formatters, a DateTimeParseException is thrown.
 * <p>
 * The class uses a set of custom date-time formatters to handle various non-ISO-8601 date-time representations.
 * These formatters cover common ways users might input a zoned date-time.
 * <p>
 * Subject to additions if need be...
 * 
 * @author chobot
 * @version 1.0
 */
public class ZonedDateTimeStringParser {
	
	/**
	 * The custom date-time formatters that follow common ways in which a user might input a zoned date-time that is not ISO-8601 zoned date-time format
	 * <p>
	 * Subject to additions if need be...
	 */
	private static final List<DateTimeFormatter> FORMATTERS = new ArrayList<>();
	
	static {
		FORMATTERS.add(DateTimeFormatter.RFC_1123_DATE_TIME);
		FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
		FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z"));
		FORMATTERS.add(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy h:mm a z"));
		FORMATTERS.add(DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm a z"));
		FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy.MM.dd G 'at' HH:mm:ss z"));
		FORMATTERS.add(DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a z"));
        FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss z"));
	}
	
	/**
     * Parses a string representing a zoned date-time as a {@code ZonedDateTime}.
     *
     * @param zonedDateTimeString The date-time w/ time-zone string to parse.
     * @return The parsed zoned date-time, not null.
     * @throws DateTimeParseException 
     *         If the text cannot be parsed.
     */
	public static ZonedDateTime parse(String zonedDateTimeString) {
		return parseZonedDateTimeString(zonedDateTimeString);
    }
	
    /**
     * Parses a zoned date-time string using both ISO-8601 and custom formatters.
     * <p>
     * If the input string cannot be parsed using any of the specified formatters, a {@code DateTimeParseException} is thrown.
     *
     * @param zonedDateTimeString The date-time w/ time-zone string to parse.
     * @return The parsed zoned date-time, not null.
     * @throws DateTimeParseException 
     *         If the text cannot be parsed by any formatter.
     */
	private static ZonedDateTime parseZonedDateTimeString(String zonedDateTimeString) {
		try {
			return parseIso8601(zonedDateTimeString);
		}
		catch (DateTimeParseException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			return parseWithCustomFormatters(zonedDateTimeString);
		}
		catch (DateTimeParseException e) {
			System.out.println(e.getMessage());
		}
		
        throw handleNoFormatterPatternFit(zonedDateTimeString);
	}
	
	/**
	 * The default date time parser.
     * Obtains an instance of {@code ZonedDateTime} from a text string such as
     * {@code 2007-12-03T10:15:30+01:00[Europe/Paris]}.
     * <p>
     * The string must represent a valid date-time and is parsed using
     * {@link java.time.format.DateTimeFormatter#ISO_ZONED_DATE_TIME}.
     *
     * @param zonedDateTimeString  the text to parse such as "2007-12-03T10:15:30+01:00[Europe/Paris]", not null
     * @return the parsed zoned date-time, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
	private static ZonedDateTime parseIso8601(String zonedDateTimeString) {
		try {
			return ZonedDateTime.parse(zonedDateTimeString);
		}
		catch (DateTimeParseException e) {
			throw handleNoIso8601FormatterPatternFit(zonedDateTimeString);
		}
	}
	
	/**
	 * The custom date time parser utilizing preset patterns for common date time entries. 
	 * 
	 * @param zonedDateTimeString
	 * 		  the text to parse that does not follow the {@link java.time.format.DateTimeFormatter#ISO_ZONED_DATE_TIME} format.
	 * @return the parsed zoned date-time, not null
     * @throws DateTimeParseException 
     *         if the text cannot be parsed
	 */
	private static ZonedDateTime parseWithCustomFormatters(String zonedDateTimeString) {
		for (DateTimeFormatter formatter : FORMATTERS) {
			try {
				return ZonedDateTime.parse(zonedDateTimeString, formatter);
			}
			catch (DateTimeParseException e) {
				continue;
			}
		}
		throw handleNoCustomFormatterPatternFit(zonedDateTimeString);
	}
	
	/**
	 * Handles the case when none of the formatter patterns fit for parsing the input date-time.
	 *
	 * @param zonedDateTimeString The input date-time string that failed to be parsed by all formatter patterns
	 * @return A {@code DateTimeParseException} indicating the failure
	 * @throws DateTimeParseException if none of the formatter patterns can parse the input date-time
	 */
	private static DateTimeParseException handleNoFormatterPatternFit(String zonedDateTimeString) throws DateTimeParseException {
		throw new DateTimeParseException("All formatter patterns failed to parse the input date-time.", zonedDateTimeString, 0);
	}
	
	/**
	 * Handles the case when none of the ISO-8601 formatter patterns fit for parsing the input date-time.
	 *
	 * @param zonedDateTimeString The input date-time string that failed to be parsed by all ISO-8601 formatter patterns
	 * @return A {@code DateTimeParseException} indicating the failure
	 * @throws DateTimeParseException if none of the ISO-8601 formatter patterns can parse the input date-time
	 */
	private static DateTimeParseException handleNoIso8601FormatterPatternFit(String zonedDateTimeString) throws DateTimeParseException {
		throw new DateTimeParseException("All ISO-8601 formatter patterns failed to parse the input date-time", zonedDateTimeString, 0);
	}
	
	/**
	 * Handles the case when none of the custom formatter patterns fit for parsing the input date-time.
	 *
	 * @param zonedDateTimeString The input date-time string that failed to be parsed by all custom formatter patterns
	 * @return A {@code DateTimeParseException} indicating the failure
	 * @throws DateTimeParseException if none of the custom formatter patterns can parse the input date-time
	 */
	private static DateTimeParseException handleNoCustomFormatterPatternFit(String zonedDateTimeString) throws DateTimeParseException {
		throw new DateTimeParseException("All custom formatter patterns failed to parse the input date-time.", zonedDateTimeString, 0);
	}
}
