package com.jobmarketanalyzer.job_offer_consumer.model;

import com.jobmarketanalyzer.job_offer_consumer.model.enums.SourceOffer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ref_offer_sources")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefOfferSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_name", unique = true, nullable = false)
    private SourceOffer source;
}
