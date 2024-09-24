package com.jobmarketanalyzer.job_offer_consumer.service;

import com.jobmarketanalyzer.job_offer_consumer.DTO.JsonJobOffersDTO;
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
    private final JobOfferParser freeworkParser;

    public void saveJobOfferFromKafka(JsonJobOffersDTO jsonJobOffersDTO) {
        SourceOffer sourceOffer = SourceOffer.getSourceFromString(jsonJobOffersDTO.source());

        jobOfferRepository.saveAll(switch (sourceOffer) {
                    case INDEED -> indeedParser.parseJobOffers(jsonJobOffersDTO.jobsJson());
                    case FREEWORK -> freeworkParser.parseJobOffers(jsonJobOffersDTO.jobsJson());
                    case FRANCE_TRAVAIL -> franceTravailParser.parseJobOffers(jsonJobOffersDTO.jobsJson());
                }
        );
    }

}
