package com.chobot.timezonecoverter.timezoneconverterapp.utils;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.Test;
//
//import com.chobot.timezonecoverter.timezoneconverterapp.datetime.UserInputDateTimeConverter;
//
//public class UserInputDateTimeConverterTests {
//	
//	@Test
//	public void testValidUserInput() {
//		String mockUserInput = createMockUserInput("2010-02-04T14:39:28 PST", "EST");
//		String formattedDateTime = UserInputDateTimeConverter.convertInputToFormattedDateTimeString(mockUserInput);
//		
//		assertEquals(formattedDateTime, createExpectedOutput(mockUserInput, "Feb 04, 2010 5:39 PM EST"));
//	}
//	
//	@Test
//	public void testCaseSensitivityTimeZone() {
//		String mockUserInput = createMockUserInput("2010-02-04T14:39:28 PST", "est");
//		String formattedDateTime = UserInputDateTimeConverter.convertInputToFormattedDateTimeString(mockUserInput);
//
//		assertEquals(formattedDateTime, createExpectedOutput(mockUserInput, "Feb 04, 2010 5:39 PM EST"));
//	}
//	
//	@Test
//	public void testInvalidTargetZoneId() {
//		String mockUserInput = createMockUserInput("2010-02-04T14:39:28 PST", "fail");
//		String formattedDateTime = UserInputDateTimeConverter.convertInputToFormattedDateTimeString(mockUserInput);
//
//		assertEquals(formattedDateTime, "Invalid zone ID format. Please provide a valid zone ID.");
//	}
//	
//	@Test
//	public void testInvalidDateTimeFormat() {
//		String mockUserInput = createMockUserInput("02-04-2010 10:30 PST", "EST");
//		String formattedDateTime = UserInputDateTimeConverter.convertInputToFormattedDateTimeString(mockUserInput);
//		
//		assertEquals(formattedDateTime, "Invalid date/time format. Please provide a valid date/time.");
//	}
//	
//	private String createMockUserInput(String dateTimeToConvert, String targetTimeZone) {
//		return String.format("%s to %s", dateTimeToConvert, targetTimeZone);
//	}
//	
//	private String createExpectedOutput(String mockUserInput, String expectedDateTime) {
//		return String.format("%s is *%s*.", mockUserInput, expectedDateTime);
//	}
//}
