package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

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
	 * Parses a string representing a zoned date-time as a {@code ZonedDateTime}
	 * <p>
	 * @param zonedDateTimeStr date-time w/ time-zone
	 * @return the parsed zoned-date-time, not null
	 * @throws DateTimeParseException - if the text cannot be parsed
	 */
	public static ZonedDateTime parse(String zonedDateTimeString) {
		try {
			return parseIso8601(zonedDateTimeString);
		}
		catch (DateTimeParseException e) {
			System.out.println("Iso8601 parsing failed.");
		}
		
		try {
			return parseWithCustomFormatters(zonedDateTimeString);
		}
		catch (DateTimeParseException e) {
			System.out.println(e.getMessage());
		}
		
        throw new DateTimeParseException("All formatter patterns failed to parse the input date-time.", zonedDateTimeString, 0);	
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
		return ZonedDateTime.parse(zonedDateTimeString);
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
		throw new DateTimeParseException ("All custom formatter patterns failed to parse the input date-time.", zonedDateTimeString, 0);
	}
}
