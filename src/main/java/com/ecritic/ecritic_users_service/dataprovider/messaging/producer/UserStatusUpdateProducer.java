package com.ecritic.ecritic_users_service.dataprovider.messaging.producer;

import com.ecritic.ecritic_users_service.config.properties.KafkaProperties;
import com.ecritic.ecritic_users_service.dataprovider.messaging.entity.UserStatusUpdateMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserStatusUpdateProducer {

    @Autowired
    private KafkaTemplate<String, UserStatusUpdateMessage> kafkaTemplate;

    @Autowired
    private KafkaProperties kafkaProperties;

    public void execute(UserStatusUpdateMessage userStatusUpdateMessage) {
        log.info("Sending message to topic: [{}]", kafkaProperties.getUserStatusUpdateTopic());

        try {
            kafkaTemplate.send(kafkaProperties.getUserStatusUpdateTopic(), userStatusUpdateMessage);
        } catch (Exception e) {
            log.error("Error when sending user status update message", e);
        }
    }
}
