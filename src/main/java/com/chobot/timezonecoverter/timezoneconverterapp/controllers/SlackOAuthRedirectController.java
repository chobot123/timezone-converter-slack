package com.chobot.timezonecoverter.timezoneconverterapp.controllers;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jakarta_servlet.SlackOAuthAppServlet;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/slack/oauth_redirect")
public class SlackOAuthRedirectController extends SlackOAuthAppServlet {
	public SlackOAuthRedirectController(App app) { super(app); }
}
