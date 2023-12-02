package com.chobot.timezonecoverter.timezoneconverterapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SlackOAuthCallBackController {
	
	@GetMapping("/slack/oauth/completion")
	public String handleCompletion() {
		System.out.println("completed");
		return "forward:/static/completion.html";
	}

	@GetMapping("/slack/oauth/cancellation")
	public String handleCancellation() {
		System.out.println("cancelled");
		return "forward:/static/cancellation.html";
	}
}
