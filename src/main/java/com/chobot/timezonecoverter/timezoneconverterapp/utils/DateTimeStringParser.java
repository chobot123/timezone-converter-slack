package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DateTimeStringParser {
	
	private static final List<DateTimeFormatter> FORMATTERS = new ArrayList<>();
	
	static {

		FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
		FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z"));
		FORMATTERS.add(DateTimeFormatter.RFC_1123_DATE_TIME);
		FORMATTERS.add(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy h:mm a z"));
		FORMATTERS.add(DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm a z"));
		FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy.MM.dd G 'at' HH:mm:ss z"));
		FORMATTERS.add(DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a z")); 
	}
	
	/**
	 * Parses string DateTime w/ TimeZone as a ZonedDateTime obj
	 * <p>
	 * @param zonedDateTimeStr date-time w/ time-zone
	 * @return the parsed zoned-date-time, not null
	 * @throws DateTimeParseException - if the text cannot be parsed
	 */
	public static ZonedDateTime parse(String zonedDateTimeStr) {
		
		try {
			return ZonedDateTime.parse(zonedDateTimeStr);
		}
		catch (DateTimeParseException e) {
			System.out.println("ISO-8601 formatters failed: " + e.getMessage());
		}
		
		for (DateTimeFormatter formatter : FORMATTERS) {
			try {
				return ZonedDateTime.parse(zonedDateTimeStr, formatter);
			}
			catch (DateTimeParseException e) {
				System.out.println("Custom formatter pattern failed: " + e.getMessage());
			}
		}
		throw new DateTimeParseException ("All formatter patterns failed to parse the input date-time.", zonedDateTimeStr, 0);
	}
}
