package com.jobmarketanalyzer.job_offer_consumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmarketanalyzer.job_offer_consumer.DTO.JsonJobOffersDTO;
import com.jobmarketanalyzer.job_offer_consumer.service.JobOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaJobOfferListener {

    private final ObjectMapper objectMapper;
    private final JobOfferService jobOfferService;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    void listen(String jobOffersMessage) {
        try {
            log.info("Received Job Offers from Kafka : {} ", jobOffersMessage);

            JsonJobOffersDTO jsonJobOffersDTO = objectMapper.readValue(jobOffersMessage, JsonJobOffersDTO.class);

            log.info("Successfully mapped message to JsonJobOffersDTO.");

            jobOfferService.saveJobOfferFromKafka(jsonJobOffersDTO);

        } catch (JsonProcessingException e) {
            log.error("Error mapping Kafka message to JobOffersDTO. Message: {}.", jobOffersMessage, e);

        }
    }
}
