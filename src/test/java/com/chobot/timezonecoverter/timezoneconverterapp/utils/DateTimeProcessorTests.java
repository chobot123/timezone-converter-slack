package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.chobot.timezonecoverter.timezoneconverterapp.datetime.DateTimeProcessor;

public class DateTimeProcessorTests {
	@Test
	public void testValidUserInput() {
		String mockUserInput = createMockUserInput("2010-02-04T14:39:28 PST", "EST");
		String formattedDateTime = DateTimeProcessor.processDateTimeInput(mockUserInput);
		
		assertEquals(createExpectedOutput(mockUserInput, "Feb 04, 2010 5:39 PM EST"), formattedDateTime);
	}
	
	@Test
	public void testCaseSensitivityTimeZone() {
		String mockUserInput = createMockUserInput("2010-02-04T14:39:28 PST", "est");
		String formattedDateTime = DateTimeProcessor.processDateTimeInput(mockUserInput);

		assertEquals(createExpectedOutput(mockUserInput, "Feb 04, 2010 5:39 PM EST"), formattedDateTime);
	}
	
	@Test
	public void testInvalidTargetZoneId() {
		String mockUserInput = createMockUserInput("2010-02-04T14:39:28 PST", "fail");
		String formattedDateTime = DateTimeProcessor.processDateTimeInput(mockUserInput);

		assertEquals("Zone ID region ID for: FAIL could not be found.", formattedDateTime);
	}
	
	@Test
	public void testInvalidDateTimeFormat() {
		String mockUserInput = createMockUserInput("02-04-2010 10:30 PST", "EST");
		String formattedDateTime = DateTimeProcessor.processDateTimeInput(mockUserInput);
		
		assertEquals("The format of the date time is invalid: 02-04-2010 10:30 PST", formattedDateTime);
	}
	
	private String createMockUserInput(String dateTimeToConvert, String targetTimeZone) {
		return String.format("%s to %s", dateTimeToConvert, targetTimeZone);
	}
	
	private String createExpectedOutput(String mockUserInput, String expectedDateTime) {
		return String.format("%s is *%s*.", mockUserInput, expectedDateTime);
	}
}
