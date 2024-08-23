package com.jobmarketanalyzer.job_offer_consumer.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SourceOffer {
    FRANCE_TRAVAIL,
    INDEED;

    public static SourceOffer getSourceFromString(String source){
        for (SourceOffer sourceOffer : SourceOffer.values()){
            if (sourceOffer.name().equalsIgnoreCase(source)){
                return sourceOffer;
            }
        }
        throw new IllegalArgumentException("Unknown Source : " + source);
    }
}
