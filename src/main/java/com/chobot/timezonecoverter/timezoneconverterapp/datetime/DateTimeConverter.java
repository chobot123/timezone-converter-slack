package com.chobot.timezonecoverter.timezoneconverterapp.datetime;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;

/**
 * A utility class for converting {@code ZonedDateTime} instances to the same instant with a target time-zone.
 * The target time-zone can be specified either by ZoneID or using ZoneId.SHORT_IDS.
 * <p>
 * The class provides a {@code convert} method to perform the conversion, handling various exceptions
 * such as invalid time-zone format, region ID not found, and invalid date ranges during the conversion.
 * </p>
 *
 * @author chobot
 * @version 1.0
 * @since 2023-12-04
 */
public class DateTimeConverter {
	/**
	 * Converts the {@code ZonedDateTime} to the same instant with the target time-zone (ZoneID or by ZoneId.SHORT_IDS)
	 * 
	 * @param zonedDateTime 
	 * 		  the zoned date-time object to convert, not null
	 * @param targetTimeZoneString
	 * 		  the target time-zone, not null
     * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
     * @throws DateTimeException 
     *         If the zone id is not found
     * @throws IllegalArgumentException 
     *         If the zone ID has an invalid format, or zone ID region ID not found
	 */
	public static ZonedDateTime convert(ZonedDateTime zonedDateTime, String targetTimeZoneString) {
		ZoneId targetTimeZone = getZoneId(targetTimeZoneString);
		return convertToTargetTimeZone(zonedDateTime, targetTimeZone);
	}
	
	/**
	 * Converts the date-time to the same instant as the target time-zone (Zone ID)
	 * @param zonedDateTime 
	 *        the zoned date-time object to convert, not null
	 * @param targetTimeZone
	 * 		  the target time-zone, not null
	 * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
	 * @throws DateTimeException
	 * 		   If the result exceeds the supported date range
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
	 * Converts a time zone string as a {@code ZoneId}
	 * @param timeZoneString
	 * 		  time zone string, not null (usually in short form i.e. "EST", "PST")
	 * @return a {@code ZoneId}, not null
	 * @throws IllegalArgumentException
	 * 		   If the zone ID has an invalid format, or zone ID region ID not found
	 */

	private static ZoneId getZoneId(String timeZoneString) {
		try {
			String timeZoneRegion = ZoneId.SHORT_IDS.get(timeZoneString);
			if (timeZoneRegion == null) {
				return ZoneId.of(timeZoneString);
			}
			return ZoneId.of(timeZoneRegion);
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
	 * @throws IllegalArgumentException 
	 * 		   If the exception is related to an invalid zone ID or region ID
	 */
	private static IllegalArgumentException handleZoneIdException(String timeZoneString, DateTimeException e) {
	    if (e instanceof ZoneRulesException) {
	        throw handleRegionIdNotFound(timeZoneString, (ZoneRulesException) e);
	    } else {
	        throw handleInvalidZoneId(timeZoneString, e);
	    }
	}
	
	/**
	 * Handles exceptions when the ZoneId has an invalid format.
	 *
	 * @param timeZoneString The time zone string causing the exception
	 * @param e The DateTimeException indicating the issue
	 * @throws IllegalArgumentException 
	 *         with a descriptive error message
	 */
	private static IllegalArgumentException handleInvalidZoneId(String timeZoneString, DateTimeException e) throws IllegalArgumentException {
		throw new IllegalArgumentException("Zone ID: " + timeZoneString + " has an invalid format.", e);
	}
	
	/**
	 * Handles exceptions when the ZoneId region ID could not be found.
	 *
	 * @param timeZoneString The time zone string causing the exception
	 * @param e The DateTimeException indicating the issue
	 * @throws IllegalArgumentException 
	 *         with a descriptive error message
	 */
	private static IllegalArgumentException handleRegionIdNotFound(String timeZoneString, ZoneRulesException e) throws IllegalArgumentException {
		throw new IllegalArgumentException("Zone ID region ID for: " + timeZoneString + " could not be found.", e);
	}
	
	/**
	 * Handles exceptions related to invalid date range during date-time conversion.
	 *
	 * @param e The DateTimeException indicating the issue
	 * @throws DateTimeException 
	 *         with a descriptive error message
	 */
	private static DateTimeException handleInvalidDateRange(DateTimeException e) throws DateTimeException {
		throw new DateTimeException("Failed to return a copy of this date-time with a different time-zone.", e);
	}
}
