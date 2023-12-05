package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import com.chobot.timezonecoverter.timezoneconverterapp.datetime.OutputDateTimeFormatter;

public class OutputDateTimeFormatterTests {
	
	@Test
	public void testOutFormat() {
        ZonedDateTime validZonedDateTime = ZonedDateTime.parse("2023-06-15T10:30:00+02:00");
		String validInput = "2011-12-03T10:15:30Z to PST";
		String validTargetTimeZone = "PST";
		
		String formattedOutput = OutputDateTimeFormatter.formatZonedDateTimeToOutputString(validZonedDateTime, validInput, validTargetTimeZone);
		assertEquals("2011-12-03T10:15:30Z to PST is *Jun 15, 2023 10:30 AM PST*.", formattedOutput);
	}
}
