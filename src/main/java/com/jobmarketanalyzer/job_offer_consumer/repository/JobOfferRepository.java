package com.jobmarketanalyzer.job_offer_consumer.repository;

import com.jobmarketanalyzer.job_offer_consumer.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
}
