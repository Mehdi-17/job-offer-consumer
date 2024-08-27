package com.jobmarketanalyzer.job_offer_consumer.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmarketanalyzer.job_offer_consumer.exception.JobOfferParseException;
import com.jobmarketanalyzer.job_offer_consumer.model.JobOffer;
import com.jobmarketanalyzer.job_offer_consumer.model.RefOfferSource;
import com.jobmarketanalyzer.job_offer_consumer.model.enums.SourceOffer;
import com.jobmarketanalyzer.job_offer_consumer.parser.JobOfferParser;
import com.jobmarketanalyzer.job_offer_consumer.repository.RefOfferSourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
@Slf4j
public class IndeedParser implements JobOfferParser {

    private final ObjectMapper objectMapper;
    private final RefOfferSourceRepository refOfferSourceRepository;

    @Override
    public List<JobOffer> parseJobOffers(String jobJson) {
        try {
            //todo parser en objet DTO
            List<JobOffer> jobOfferList = objectMapper.readValue(jobJson, new TypeReference<>() {
            });

            RefOfferSource source = refOfferSourceRepository.findBySource(SourceOffer.INDEED)
                    .orElseThrow(() -> new NoSuchElementException("Source " + SourceOffer.INDEED + " not found in the database."));

            jobOfferList.forEach(jobOffer -> {
                jobOffer.setSource(source);
                jobOffer.setDescription(cleanText(jobOffer.getDescription()));
                jobOffer.setDate(LocalDate.now());
                //todo faire une création de joboffer avec build en essayant de parser les salaire
            });

            return jobOfferList;
        } catch (JsonProcessingException e) {
            log.error("Unable to parse Job offers from json.", e);
            throw new JobOfferParseException("Failed to parse job offers from Indeed JSON", e);
        }
    }

    private String cleanText(String textWithHtml) {
        if (textWithHtml == null) {
            return null;
        }

        String withoutHtml = textWithHtml.replaceAll("<[^>]*>", "");
        return withoutHtml.replaceAll("[\n\r\t]+", " ").trim();
    }
}
