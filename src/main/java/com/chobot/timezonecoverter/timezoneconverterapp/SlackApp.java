package com.chobot.timezonecoverter.timezoneconverterapp;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chobot.timezonecoverter.timezoneconverterapp.utils.DateTimeStringParser;
import com.slack.api.bolt.App;

@Configuration
public class SlackApp {
    
	@Bean
	public App initSlackApp() {
		App app	= new App();
		app.command("/hello", (req, ctx) -> {
			System.out.println("Request received...");
			return ctx.ack("What's up?");
		});
		
		app.command("/convert", (req, ctx) -> {
			String text = req.getPayload().getText();
			String[] textSplit = text.split("to");
			String dateTimeString = textSplit[0].trim();
			String timeZoneToConvertTo = textSplit[1].trim();
			
			ZonedDateTime zonedDateTime = DateTimeStringParser.parse(dateTimeString);
			
			// conver to other timezone
			ZonedDateTime convertedZonedDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.of(timeZoneToConvertTo, ZoneId.SHORT_IDS));
			String formattedZonedDateTime = convertedZonedDateTime.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm a"));
			return ctx.ack(text + " is *"+ formattedZonedDateTime + " " + timeZoneToConvertTo + "*.");
		});

		app.command("/test", (req, ctx) -> {
			return ctx.ack("test");
		});
		return app;
	}
}
