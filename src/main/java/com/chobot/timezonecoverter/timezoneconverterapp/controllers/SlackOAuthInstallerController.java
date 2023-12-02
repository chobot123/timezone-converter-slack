package com.chobot.timezonecoverter.timezoneconverterapp.controllers;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jakarta_servlet.SlackOAuthAppServlet;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/slack/install")
public class SlackOAuthInstallerController extends SlackOAuthAppServlet {
	public SlackOAuthInstallerController(App app) { super(app); }
}
