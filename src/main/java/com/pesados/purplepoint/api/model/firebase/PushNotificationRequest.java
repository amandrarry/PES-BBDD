package com.pesados.purplepoint.api.model.firebase;

import io.swagger.v3.oas.annotations.media.Schema;

public class PushNotificationRequest {

    @Schema(description = "Title of the notification that is gonna be send.", example = "Your help is needed", required = true)
    private String title;
    @Schema(description = "Message of the notification that is gonna be send", example = "The user XYZ needs your help", required = true)
    private String message;
    @Schema(description = "Name of the subscription topic to which we want to send a notification", example = "BarcelonaHelp", required = false)
    private String topic;
    @Schema(description = "Token of the device to which we want to send a notification", example = "ffudfll77dfhdkfos", required = false)
    private String token;

    public PushNotificationRequest() {
    }

    public PushNotificationRequest(String title, String messageBody) {
        this.title = title;
        this.message = messageBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}