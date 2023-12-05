# Timezone Converter Slack Application

## Overview

Welcome to the Timezone Converter Slack Application! This application is built using Spring Boot and provides a convenient way to convert timezones within your Slack workspace. With simple commands, you can say hello or convert time between different timezones.

## Prerequisites

Before you start, make sure you have the following prerequisites:

- Java 17
- Maven
- Slack App Credentials (Refer to the "Refer to this for require env files" section below)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd timezone-converter-app
```

### 2. Set Up Slack App Credentials

Create a .env file in the project root and add the following environment variables: (Or configure the way you want)
```bash
SLACK_CLIENT_ID=<your-slack-client-id>
SLACK_CLIENT_SECRET=<your-slack-client-secret>
SLACK_SIGNING_SECRET=<your-slack-signing-secret>
SLACK_SCOPES=<your-slack-scopes>
DOMAIN_URL=<your-domain-url>
SLACK_OAUTH_REDIRECT_PATH=<your-oauth-redirect-path>
SLACK_INSTALL_PATH=<your-slack-install-path>
SLACK_OAUTH_COMPLETION_PATH=<your-oauth-completion-path>
SLACK_OAUTH_CANCELLATION_PATH=<your-oauth-cancellation-path>
```

### 3. Build and Run the Application
```bash
mvn clean install
java -jar target/timezone-converter-app-0.0.1-SNAPSHOT.jar
```

### 4. Slack App Configurations

Go to **https://api.slack.com/apps** to configure your application settings. 
#### TO CREATE A DISTRIBUTION URL: 
```bash
<domain url>/slack/install
```
#### The default configuration listed on their app api page does not work for Spring Boot + Bolt SDK

## Slack Commands

### 1. Hello Command
Use /hello to greet the Timezone Converter App.
```bash
/hello
```

### 2. Convert Command
Use /convert to initiate the timezone conversion process.
```bash
/convert
```

## Questions or Issues

If you have any questions or encounter issues, feel free to [open an issue](<repository-url>/issues) on GitHub.

Happy timezone converting! 🌐


