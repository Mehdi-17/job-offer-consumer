package com.jobmarketanalyzer.job_offer_consumer.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmarketanalyzer.job_offer_consumer.exception.JobOfferParseException;
import com.jobmarketanalyzer.job_offer_consumer.model.JobOffer;
import com.jobmarketanalyzer.job_offer_consumer.model.RefOfferSource;
import com.jobmarketanalyzer.job_offer_consumer.model.enums.SourceOffer;
import com.jobmarketanalyzer.job_offer_consumer.parser.JobOfferParser;
import com.jobmarketanalyzer.job_offer_consumer.repository.RefSourceOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
@Slf4j
public class IndeedParser implements JobOfferParser {

    private final ObjectMapper objectMapper;
    private final RefSourceOfferRepository refSourceOfferRepository;

    @Override
    public List<JobOffer> parseJobOffers(String jobJson) {
        try {
            List<JobOffer> jobOffers = objectMapper.readValue(jobJson, new TypeReference<List<JobOffer>>() {
            });

            RefOfferSource source = refSourceOfferRepository.findBySource(SourceOffer.INDEED)
                    .orElseThrow(() -> new NoSuchElementException("Source " + SourceOffer.INDEED + " not found in the database."));

            jobOffers.forEach(jobOffer -> jobOffer.setSource(source));

            return jobOffers;
        } catch (JsonProcessingException e) {
            log.error("Unable to parse Job offers from json.", e);
            throw new JobOfferParseException("Failed to parse job offers from Indeed JSON", e);
        }
    }
}
