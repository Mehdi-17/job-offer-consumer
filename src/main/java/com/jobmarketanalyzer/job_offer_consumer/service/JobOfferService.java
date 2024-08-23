package com.jobmarketanalyzer.job_offer_consumer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmarketanalyzer.job_offer_consumer.DTO.JobOffersDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobOfferService {

    private final ObjectMapper objectMapper;

    public void saveJobOfferFromKafka(JobOffersDTO jobOffersDTO){
        //todo: implement logic

    }

}
