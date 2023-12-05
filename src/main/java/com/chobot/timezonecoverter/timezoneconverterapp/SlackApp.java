package com.chobot.timezonecoverter.timezoneconverterapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chobot.timezonecoverter.timezoneconverterapp.datetime.DateTimeProcessor;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;

@Configuration
public class SlackApp {

    @Bean
    public AppConfig loadOAuthConfig() {
        return AppConfig.builder()
                .singleTeamBotToken(null)
                .clientId(System.getenv("SLACK_CLIENT_ID"))
                .clientSecret(System.getenv("SLACK_CLIENT_SECRET"))
                .signingSecret(System.getenv("SLACK_SIGNING_SECRET"))
                .scope("commands,metadata.message:read,chat:write")
                .redirectUri(System.getenv("SLACK_REDIRECT_URI"))
                .oauthInstallPath("/slack/install")
                .oauthRedirectUriPath("/slack/oauth_redirect")
                .oauthCompletionUrl(System.getenv("SLACK_OAUTH_COMPLETION_URL"))
                .oauthCancellationUrl(System.getenv("SLACK_OAUTH_CANCELLATION_URL"))
                .build();
    }
    
	@Bean
	public App initSlackApp(AppConfig config) {
		App app = new App(config);
        if (config.getClientId() != null) {
            app.asOAuthApp(true);
        }
        
		app.command("/hello", (req, ctx) -> {
			System.out.println("Request received...");
			return ctx.ack("What's up?");
		});
		
		app.command("/convert", (req, ctx) -> {
			System.out.println("running convert command.");
			return ctx.ack(DateTimeProcessor.processDateTimeInput(req.getPayload().getText()));
		});
		
		return app;
	}
}
