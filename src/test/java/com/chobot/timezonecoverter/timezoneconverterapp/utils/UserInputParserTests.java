package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.chobot.timezonecoverter.timezoneconverterapp.datetime.UserInputParser;

public class UserInputParserTests {

	@Test
	public void testValidUserInput() {
		String validUserInput = "2010-02-04T14:39:28 PST to EST";
		assertAll(
			() -> assertNotNull(UserInputParser.parse(validUserInput)),
			() -> assertDoesNotThrow(() -> UserInputParser.parse(validUserInput))
		);
	}
	
	@Test
	public void testInvalidUserInput() {
		String inValidUserInput = "adadadsaddsa";
		assertThrows(
			IllegalArgumentException.class,
			() -> UserInputParser.parse(inValidUserInput)
		);
	}
	
	@Test
	public void testNullUserInput() {
		String inValidUserInput = null;
		assertThrows(
			IllegalArgumentException.class,
			() -> UserInputParser.parse(inValidUserInput)
		);
	}
	
	@Test
	public void testEmptyUserInput() {
		String inValidUserInput = "";
		assertThrows(
			IllegalArgumentException.class,
			() -> UserInputParser.parse(inValidUserInput)
		);
	}
	
	@Test
	public void testInvalidDateTimeFormat() {
		String inValidUserInput = "2010-02-04T14:39:28 peepee to EST";
		assertThrows(
			IllegalArgumentException.class,
			() -> UserInputParser.parse(inValidUserInput)
		);
	}
}
