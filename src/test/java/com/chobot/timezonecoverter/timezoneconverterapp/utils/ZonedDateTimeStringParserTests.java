package com.chobot.timezonecoverter.timezoneconverterapp.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class ZonedDateTimeStringParserTests {
	
	@Test
	public void testParsingWithIsoInstant() {
		String validDateTime = "2011-12-03T10:15:30Z";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);

	    assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime));
	}
	
	@Test
	public void testParsingWithIsoOffsetDateTime() {
		String validDateTime = "2011-12-03T10:15:30+01:00";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);
		
	    assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime));
	}
	
	@Test
	public void testParsingWithPatternYMD_HMS_z() {
		String validDateTime = "2023-11-28 15:30:00 +03:00";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);
		
	    assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")));
	}
	
	@Test
	public void testParsingWithPatternYMD_HMS_Z() {
		String validDateTime = "2023-11-28 15:30:00 +0300";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);
		
	    assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")));
	}
	
	@Test
	public void testParsingWithPatternRFC_1123() {
		String validDateTime = "Tue, 3 Jun 2008 11:05:30 GMT";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);

		assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime, DateTimeFormatter.RFC_1123_DATE_TIME));
	}
	
	@Test
	public void testParsingWithPatternE_MD_Y_HMA_z() {
		String validDateTime = "Tue, Nov 28, 2023 3:30 PM UTC";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);
		
	    assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime, DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy h:mm a z")));
	}
	
	@Test
	public void testParsingWithPatternMD_Y_HMA_z() {
		String validDateTime = "November 28, 2023 3:30 PM UTC";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);
		
	    assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime, DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm a z")));
	}
	
	@Test
	public void testParsingWithPatternYMD_G_at_HMS_z() {
		String validDateTime = "2023.11.28 AD at 15:30:00 UTC";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);
		
	    assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime, DateTimeFormatter.ofPattern("yyyy.MM.dd G 'at' HH:mm:ss z")));
	}
	
	@Test
	public void testParsingWithPatternMD_Y_HM_A_z() {
		String validDateTime = "Nov 28, 2023 3:30 PM EST";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);
		
	    assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime, DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a z")));
	}
	
	@Test
	public void testParsingWithPatternYMD_T_HMS_z() {
		String validDateTime = "2010-02-04T14:39:28 PST";
		ZonedDateTime parsedZonedDateTime = ZonedDateTimeStringParser.parse(validDateTime);
		
	    assertEquals(parsedZonedDateTime, ZonedDateTime.parse(validDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss z")));
	}
}
