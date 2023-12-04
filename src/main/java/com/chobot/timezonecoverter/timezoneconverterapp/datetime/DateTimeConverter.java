package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;

public class DateTimeConverter {
	/**
	 * Converts the {@code ZonedDateTime} to the same instant with the target time-zone (ZoneID or by ZoneId.SHORT_IDS)
	 * 
	 * @param zonedDateTime 
	 * 		  the zoned date-time object to convert, not null
	 * @param targetTimeZoneString
	 * 		  the target time-zone, not null
     * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
     * @throws DateTimeException if the zone id is not found
	 */
	public static ZonedDateTime convert(ZonedDateTime zonedDateTime, String targetTimeZoneString) {
		ZoneId targetTimeZone = getZoneId(targetTimeZoneString);
		
		if (targetTimeZone != null) {
			return convertToTargetTimeZone(zonedDateTime, targetTimeZone);
		}
		
		return convertToAlternativeTimeZone(zonedDateTime, targetTimeZoneString);
	}
	
	/**
	 * Converts the date-time to the same instant as the target time-zone (Zone ID)
	 * @param zonedDateTime 
	 *        the zoned date-time object to convert, not null
	 * @param targetTimeZone
	 * 		  the target time-zone, not null
	 * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
	 * @throws DateTimeException
	 * 		   if the result exceeds the supported date range
	 */
	private static ZonedDateTime convertToTargetTimeZone(ZonedDateTime zonedDateTime, ZoneId targetTimeZone) {
		try {
			return zonedDateTime.withZoneSameInstant(targetTimeZone);
		}
		catch (DateTimeException e) {
			throw handleInvalidDateRange(e);
		}
	}
	
	/**
	 * Converts the zoned date-time to the same instant as the target time zone (ZoneId.SHORT_IDS)
	 * <p>
	 * 
	 * This is for when the target time zone is a string, primarily for short_ids i.e. "EST", "PST", etc.
	 * @Overload {@code convertToTargetTimeZone(ZonedDateTime zonedDateTime, ZoneId targetTimeZone)}
	 * <p>
	 * @param zonedDateTime 
	 *        the zoned date-time object to convert, not null
	 * @param targetTimeZoneString
	 * 		  the target time-zone as a string, not null
	 * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
	 * @throws ClassCastException
	 *         if the key is of an inappropriate type for this map(optional)
	 * @throws NullPointerException
	 *         if the specified key is null and this map does not permit null keys(optional)
	 */
	private static ZonedDateTime convertToAlternativeTimeZone(ZonedDateTime zonedDateTime, String targetTimeZoneString) {
		
		try {
			String timeZoneRegion = ZoneId.SHORT_IDS.get(targetTimeZoneString);
			ZoneId alternativeTimeZone = getZoneId(timeZoneRegion);
			return convertToTargetTimeZone(zonedDateTime, alternativeTimeZone);
		}
		catch (NullPointerException e) {
			throw handleTimeZoneRegionIdNotFound(targetTimeZoneString, e);
		}
		catch (IllegalArgumentException e) {
			throw e;
		}
		catch (DateTimeException e) {
			throw e;
		}
	}
	
	/**
	 * Converts a time zone string as a {@code ZoneId}
	 * @param timeZoneString
	 * 		  time zone string, not null (usually in short form i.e. "EST", "PST")
	 * @return a {@code ZoneId}, not null
	 * @throws IllegalArgumentException
	 * 		   if the zone ID has an invalid format, or zone ID region ID not found
	 */
	private static ZoneId getZoneId(String timeZoneString) {
		try {
			return ZoneId.of(timeZoneString);
		}
		catch (DateTimeException e) {
			throw handleZoneIdException(timeZoneString, e);
		}
	}
	
	/**
	 * Handles exceptions related to ZoneId conversion, providing specific handling for ZoneRulesException
	 * and other DateTimeException cases.
	 *
	 * @param timeZoneString The time zone string causing the exception
	 * @param e The DateTimeException indicating the issue
	 * @throws IllegalArgumentException if the exception is related to an invalid zone ID or region ID
	 */
	private static IllegalArgumentException handleZoneIdException(String timeZoneString, DateTimeException e) {
	    if (e instanceof ZoneRulesException) {
	        throw handleRegionIdNotFound(timeZoneString, e);
	    } else {
	        throw handleInvalidZoneId(timeZoneString, e);
	    }
	}
	
	/**
	 * Handles exceptions when the ZoneId has an invalid format.
	 *
	 * @param timeZoneString The time zone string causing the exception
	 * @param e The DateTimeException indicating the issue
	 * @throws IllegalArgumentException with a descriptive error message
	 */
	private static IllegalArgumentException handleInvalidZoneId(String timeZoneString, DateTimeException e) throws IllegalArgumentException {
		throw new IllegalArgumentException("Zone ID: " + timeZoneString + " has an invalid format.", e);
	}
	
	/**
	 * Handles exceptions when the ZoneId region ID could not be found.
	 *
	 * @param timeZoneString The time zone string causing the exception
	 * @param e The DateTimeException indicating the issue
	 * @throws IllegalArgumentException with a descriptive error message
	 */
	private static IllegalArgumentException handleRegionIdNotFound(String timeZoneString, DateTimeException e) throws IllegalArgumentException {
		throw new IllegalArgumentException("Zone ID region ID for : " + timeZoneString + " could not be found.", e);
	}
	
	/**
	 * Handles exceptions when the region ID is not found, typically due to a NullPointerException.
	 *
	 * @param timeZoneString The time zone string causing the exception
	 * @param e The NullPointerException indicating the issue
	 * @throws IllegalArgumentException with a descriptive error message
	 */
	private static IllegalArgumentException handleTimeZoneRegionIdNotFound(String timeZoneString, NullPointerException e) throws IllegalArgumentException {
		throw new IllegalArgumentException("Region ID for: " + timeZoneString + " not found.", e);
	}
	
	/**
	 * Handles exceptions related to invalid date range during date-time conversion.
	 *
	 * @param e The DateTimeException indicating the issue
	 * @throws DateTimeException with a descriptive error message
	 */
	private static DateTimeException handleInvalidDateRange(DateTimeException e) throws DateTimeException {
		throw new DateTimeException("Failed to return a copy of this date-time with a different time-zone.", e);
	}
}
