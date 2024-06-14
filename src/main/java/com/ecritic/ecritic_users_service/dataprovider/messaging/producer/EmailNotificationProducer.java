package com.ecritic.ecritic_users_service.dataprovider.messaging.producer;

import com.ecritic.ecritic_users_service.config.properties.KafkaProperties;
import com.ecritic.ecritic_users_service.dataprovider.messaging.entity.EmailNotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailNotificationProducer {

    @Autowired
    private KafkaTemplate<String, EmailNotificationMessage> kafkaTemplate;

    @Autowired
    private KafkaProperties kafkaProperties;

    public void execute(EmailNotificationMessage emailNotificationMessage) {
        log.info("Sending message to topic: [{}]", kafkaProperties.getEmailNotificationTopic());

        try {
            kafkaTemplate.send(kafkaProperties.getEmailNotificationTopic(), emailNotificationMessage);
        } catch (Exception e) {
            log.error("Error when sending email notification message. Error: [{}]", e.getMessage());
        }
    }
}
