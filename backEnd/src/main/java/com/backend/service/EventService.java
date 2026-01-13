package com.backend.service;


import com.backend.dto.UserActionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final String TOPIC = "interaction-event";
    private final KafkaTemplate<String , UserActionEvent> kafkaTemplate;

    public EventService(KafkaTemplate<String , UserActionEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendEvent(UserActionEvent userActionEvent) {
        this.kafkaTemplate.send(this.TOPIC, userActionEvent);
    }
}
