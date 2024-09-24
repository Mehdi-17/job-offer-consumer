package com.jobmarketanalyzer.job_offer_consumer.parser;

import com.jobmarketanalyzer.job_offer_consumer.DTO.JsonJobOffersDTO;
import com.jobmarketanalyzer.job_offer_consumer.model.JobOffer;

import java.util.List;

public interface JobOfferParser {
    List<JobOffer> parseJobOffers(JsonJobOffersDTO jsonJobOffersDTO);
}
