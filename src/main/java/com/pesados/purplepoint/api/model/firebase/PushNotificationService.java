package com.pesados.purplepoint.api.model.firebase;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.pesados.purplepoint.api.firebase.FCMService;
import com.pesados.purplepoint.api.model.alarm.Alarm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Service
public class PushNotificationService {

    /*
    @Value("#{${app.notifications.defaults}}")
    private Map<String, String> defaults;
    */
    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    /*
    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void sendSamplePushNotification() {
        try {
            fcmService.sendMessageWithoutData(getSamplePushNotificationRequest());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }


     */


    public void sendNotification(String token, String username) {
        try {
            Map<String, String> data = new HashMap<>();

            data.put("message", username+"are on their way to help you");
            data.put("body", "onMyWay.body");
            data.put("onMyWay", "true");
            data.put("username", username);
            fcmService.sendMessage(token, username, data);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendMulticastPushNotification(List<String> tokens, Alarm alarmNew){
        Map<String, String> data = new HashMap<>();

        data.put("latitude", String.valueOf(alarmNew.getLocation().getLatitude()));
        data.put("longitude", String.valueOf((alarmNew.getLocation().getLongitude())));
        data.put("token", alarmNew.getDeviceToken());
        data.put("title", "notification.title");
        data.put("body", "notification.body");
        data.put("onMyWay", "false");

        try {
            fcmService.sendMulticastMessageWithoutData(tokens, data);
        } catch (FirebaseMessagingException e) {
            logger.error(e.getMessage());
        }

    }

    /*
    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", defaults.get("payloadMessageId"));
        pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
        return pushData;
    }

    private PushNotificationRequest getSamplePushNotificationRequest() {
        PushNotificationRequest request = new PushNotificationRequest(defaults.get("title"),
                defaults.get("message"),
                defaults.get("topic"));
        return request;
    }

     */


}