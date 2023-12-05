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
                .scope(System.getenv("SLACK_SCOPES"))
                .redirectUri(System.getenv("DOMAIN_URL") + System.getenv("SLACK_OAUTH_REDIRECT_PATH"))
                .oauthInstallPath(System.getenv("SLACK_INSTALL_PATH"))
                .oauthRedirectUriPath(System.getenv("SLACK_OAUTH_REDIRECT_PATH"))
                .oauthCompletionUrl(System.getenv("DOMAIN_URL") + System.getenv("SLACK_OAUTH_COMPLETION_PATH"))
                .oauthCancellationUrl(System.getenv("DOMAIN_URL") + System.getenv("SLACK_OAUTH_CANCELLATION_PATH"))
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
