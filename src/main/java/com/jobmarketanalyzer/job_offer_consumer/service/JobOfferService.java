package com.jobmarketanalyzer.job_offer_consumer.service;

import com.jobmarketanalyzer.job_offer_consumer.DTO.JobOffersDTO;
import com.jobmarketanalyzer.job_offer_consumer.model.JobOffer;
import com.jobmarketanalyzer.job_offer_consumer.model.enums.SourceOffer;
import com.jobmarketanalyzer.job_offer_consumer.parser.JobOfferParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobOfferService {

    private final JobOfferParser indeedParser;
    private final JobOfferParser franceTravailParser;

    public void saveJobOfferFromKafka(JobOffersDTO jobOffersDTO) {
        SourceOffer sourceOffer = SourceOffer.getSourceFromString(jobOffersDTO.source());

        List<JobOffer> jobOffersToSave = switch (sourceOffer) {
            case INDEED -> indeedParser.parseJobOffers(jobOffersDTO);
            case FRANCE_TRAVAIL -> franceTravailParser.parseJobOffers(jobOffersDTO);
        };

        //Todo Implement Logic
    }

}
