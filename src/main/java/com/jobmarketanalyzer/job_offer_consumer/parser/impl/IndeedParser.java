package com.jobmarketanalyzer.job_offer_consumer.parser.impl;

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

        //todo transform json element to JobOffer object with objectmapper
        return null;
    }
}
