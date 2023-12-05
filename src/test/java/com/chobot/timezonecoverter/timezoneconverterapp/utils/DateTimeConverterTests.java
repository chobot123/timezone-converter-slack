package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import com.chobot.timezonecoverter.timezoneconverterapp.datetime.DateTimeConverter;

public class DateTimeConverterTests {
	
	@Test
	public void testValidTimeZone() {
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		String targetTimeZoneString = "EST";
		
		ZonedDateTime expectedZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("-05:00"));
		
		assertEquals(DateTimeConverter.convert(zonedDateTime, targetTimeZoneString), expectedZonedDateTime);
	}
	
	@Test
	public void inValidTimeZone() {
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		String invalidZone = "poo";
		assertThrows(
			IllegalArgumentException.class,
			() -> DateTimeConverter.convert(zonedDateTime, invalidZone)
		);
	}
}
