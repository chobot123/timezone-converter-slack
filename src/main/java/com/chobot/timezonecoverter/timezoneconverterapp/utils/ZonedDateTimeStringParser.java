package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ZonedDateTimeStringParser {
	
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
	 * Parses string DateTime w/ TimeZone as a ZonedDateTime obj
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
	
	private static ZonedDateTime parseIso8601(String zonedDateTimeString) {
		return ZonedDateTime.parse(zonedDateTimeString);
	}
	
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
