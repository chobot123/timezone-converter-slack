package com.chobot.timezonecoverter.timezoneconverterapp.controllers;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jakarta_servlet.SlackAppServlet;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/slack/events")
public class SlackEventsController extends SlackAppServlet {
	public SlackEventsController(App app) { super(app); }
}
