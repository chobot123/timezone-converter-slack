package com.chobot.timezonecoverter.timezoneconverterapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chobot.timezonecoverter.timezoneconverterapp.utils.UserInputDateTimeConverter;
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
			System.out.println("running convert command.");
			String text = req.getPayload().getText();
			return ctx.ack(UserInputDateTimeConverter.convertInputToFormattedDateTimeString(text));
		});
		return app;
	}
}
