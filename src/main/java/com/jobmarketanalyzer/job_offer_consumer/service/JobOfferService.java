package com.jobmarketanalyzer.job_offer_consumer.service;

import com.jobmarketanalyzer.job_offer_consumer.DTO.JsonJobOffersDTO;
import com.jobmarketanalyzer.job_offer_consumer.model.enums.SourceOffer;
import com.jobmarketanalyzer.job_offer_consumer.parser.impl.FranceTravailParser;
import com.jobmarketanalyzer.job_offer_consumer.parser.impl.ScrapingParser;
import com.jobmarketanalyzer.job_offer_consumer.repository.JobOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final ScrapingParser scrapingParser;
    private final FranceTravailParser franceTravailParser;

    public void saveJobOfferFromKafka(JsonJobOffersDTO jsonJobOffersDTO) {
        SourceOffer sourceOffer = SourceOffer.getSourceFromString(jsonJobOffersDTO.source());

        jobOfferRepository.saveAll(switch (sourceOffer) {
                    case INDEED, FREEWORK -> scrapingParser.parseJobOffers(jsonJobOffersDTO);
                    case FRANCE_TRAVAIL -> franceTravailParser.parseJobOffers(jsonJobOffersDTO);
                }
        );
    }

}
