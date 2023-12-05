package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import com.chobot.timezonecoverter.timezoneconverterapp.datetime.OutputDateTimeFormatter;

public class OutputDateTimeFormatterTests {
	
	@Test
	public void testOutFormat() {
		ZonedDateTime validZonedDateTime = ZonedDateTime.now();
		String validInput = "2011-12-03T10:15:30Z to PST";
		String validTargetTimeZone = "PST";
		
		String formattedOutput = OutputDateTimeFormatter.formatZonedDateTimeToOutputString(validZonedDateTime, validInput, validTargetTimeZone);
		assertEquals(formattedOutput, "2011-12-03T10:15:30Z to PST is *Dec 04, 2023 4:16 PM PST*.");
	}
}
