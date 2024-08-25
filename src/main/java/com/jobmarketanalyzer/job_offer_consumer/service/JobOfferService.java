package com.jobmarketanalyzer.job_offer_consumer.service;

import com.jobmarketanalyzer.job_offer_consumer.DTO.JobOffersDTO;
import com.jobmarketanalyzer.job_offer_consumer.model.enums.SourceOffer;
import com.jobmarketanalyzer.job_offer_consumer.parser.JobOfferParser;
import com.jobmarketanalyzer.job_offer_consumer.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final JobOfferParser indeedParser;
    private final JobOfferParser franceTravailParser;

    public void saveJobOfferFromKafka(JobOffersDTO jobOffersDTO) {
        SourceOffer sourceOffer = SourceOffer.getSourceFromString(jobOffersDTO.source());

        jobOfferRepository.saveAll(switch (sourceOffer) {
                    case INDEED -> indeedParser.parseJobOffers(jobOffersDTO.jobsJson());
                    case FRANCE_TRAVAIL -> franceTravailParser.parseJobOffers(jobOffersDTO.jobsJson());
                }
        );
    }

}
