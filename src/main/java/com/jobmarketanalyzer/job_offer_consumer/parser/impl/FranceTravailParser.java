package com.jobmarketanalyzer.job_offer_consumer.parser.impl;

import com.jobmarketanalyzer.job_offer_consumer.DTO.JsonJobOffersDTO;
import com.jobmarketanalyzer.job_offer_consumer.model.JobOffer;
import com.jobmarketanalyzer.job_offer_consumer.parser.JobOfferParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FranceTravailParser implements JobOfferParser {
    @Override
    public List<JobOffer> parseJobOffers(JsonJobOffersDTO jobJson) {
        //todo to implement
        return null;
    }
}
