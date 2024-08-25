package com.jobmarketanalyzer.job_offer_consumer.parser;

import com.jobmarketanalyzer.job_offer_consumer.DTO.JobOffersDTO;
import com.jobmarketanalyzer.job_offer_consumer.model.JobOffer;

import java.util.List;

public interface JobOfferParser {
    List<JobOffer> parseJobOffers(String jobJson);
}
