package com.jobmarketanalyzer.job_offer_consumer.repository;

import com.jobmarketanalyzer.job_offer_consumer.model.RefOfferSource;
import com.jobmarketanalyzer.job_offer_consumer.model.enums.SourceOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefOfferSourceRepository extends JpaRepository<RefOfferSource, Long> {
    Optional<RefOfferSource> findBySource(SourceOffer sourceOffer);

}
