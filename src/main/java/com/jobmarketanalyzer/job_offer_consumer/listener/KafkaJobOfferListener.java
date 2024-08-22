package com.jobmarketanalyzer.job_offer_consumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmarketanalyzer.job_offer_consumer.model.JobOffersDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaJobOfferListener {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    void listen(String jobOffersMessage) {
        try {
            log.info("Received Job Offers from Kafka : {} ", jobOffersMessage);

            JobOffersDTO jobOffersDTO = objectMapper.readValue(jobOffersMessage, JobOffersDTO.class);

            log.info("Successfully mapped message to JobOffersDTO.");

        } catch (JsonProcessingException e) {
            log.error("Error mapping Kafka message to JobOffersDTO. Message: {}. Error: {}", jobOffersMessage, e.getMessage());

        }
    }
}
