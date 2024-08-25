package com.jobmarketanalyzer.job_offer_consumer.config;

import com.jobmarketanalyzer.job_offer_consumer.model.RefOfferSource;
import com.jobmarketanalyzer.job_offer_consumer.model.enums.SourceOffer;
import com.jobmarketanalyzer.job_offer_consumer.repository.RefOfferSourceRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initializeData(RefOfferSourceRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                for (SourceOffer sourceOffer : SourceOffer.values()) {
                    repository.save(RefOfferSource.builder().source(sourceOffer).build());
                }
            }
        };
    }
}
