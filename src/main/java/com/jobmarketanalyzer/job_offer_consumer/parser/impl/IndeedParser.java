package com.jobmarketanalyzer.job_offer_consumer.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmarketanalyzer.job_offer_consumer.DTO.JobOffersDTO;
import com.jobmarketanalyzer.job_offer_consumer.model.JobOffer;
import com.jobmarketanalyzer.job_offer_consumer.parser.JobOfferParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class IndeedParser implements JobOfferParser {

    private final ObjectMapper objectMapper;

    @Override
    public List<JobOffer> parseJobOffers(JobOffersDTO jobOffersDTO) {
        try {
            JsonNode rootNode = objectMapper.readTree(jobOffersDTO.jobsJson());

            for (JsonNode node : rootNode) {
                String jobValue = node.get("job").asText();
                log.info("Indeed: extracting job value {}", jobValue);
                //todo continue to implement
                // we have to return a list of job offer at the end
            }

        } catch (JsonProcessingException e) {
            log.error("Error when parsing Indeed data : {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }
}
