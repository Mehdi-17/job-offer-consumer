package com.jobmarketanalyzer.job_offer_consumer.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmarketanalyzer.job_offer_consumer.DTO.JobOfferDTO;
import com.jobmarketanalyzer.job_offer_consumer.exception.JobOfferParseException;
import com.jobmarketanalyzer.job_offer_consumer.model.JobOffer;
import com.jobmarketanalyzer.job_offer_consumer.model.RefOfferSource;
import com.jobmarketanalyzer.job_offer_consumer.model.enums.SourceOffer;
import com.jobmarketanalyzer.job_offer_consumer.parser.JobOfferParser;
import com.jobmarketanalyzer.job_offer_consumer.repository.RefOfferSourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class IndeedParser implements JobOfferParser {

    private final ObjectMapper objectMapper;
    private final RefOfferSourceRepository refOfferSourceRepository;

    @Override
    public List<JobOffer> parseJobOffers(String jobJson) {
        try {
            List<JobOfferDTO> jobOfferDTOS = objectMapper.readValue(jobJson, new TypeReference<>() {
            });

            RefOfferSource source = refOfferSourceRepository.findBySource(SourceOffer.INDEED)
                    .orElseThrow(() -> new NoSuchElementException("Source " + SourceOffer.INDEED + " not found in the database."));

            return jobOfferDTOS.stream().map(jobOfferDTO -> {
                        SalaryRange minAndMaxSalaries = getMinAndMaxSalariesFromString(jobOfferDTO.dailyRate());
                        return JobOffer.builder()
                                .source(source)
                                .title(jobOfferDTO.title())
                                .date(LocalDate.now())
                                .minSalary(minAndMaxSalaries.minSalary())
                                .maxSalary(minAndMaxSalaries.maxSalary())
                                .description(cleanText(jobOfferDTO.description()))
                                .build();
                    })
                    .toList();
        } catch (JsonProcessingException e) {
            log.error("Unable to parse Job offers from json.", e);
            throw new JobOfferParseException("Failed to parse job offers from Indeed JSON", e);
        }
    }

    private String cleanText(String textWithHtml) {
        if (textWithHtml == null) {
            return null;
        }

        String withoutHtml = textWithHtml.replaceAll("<[^>]*>", "");
        return withoutHtml.replaceAll("[\n\r\t]+", " ").trim();
    }

    private SalaryRange getMinAndMaxSalariesFromString(String salaries) {
        List<Long> salariesList = extractNumbersFromString(salaries);
        Long minSalary = salariesList.isEmpty() ? 0 : salariesList.get(0);
        Long maxSalary = salariesList.size() > 1 ? salariesList.get(1) : 0;
        return new SalaryRange(minSalary, maxSalary);
    }

    private List<Long> extractNumbersFromString(String input){
        List<Long> numbers = new ArrayList<>();

        //Regex pour garder les nombre en ignorant les espaces et les séparateurs de milliers
        Pattern pattern = Pattern.compile("\\b\\d+(?:[\\s,.]\\d+)*\\b");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            // Supprimer les espaces et les séparateurs de milliers
            String numberStr = matcher.group().replaceAll("[\\s,.]", "");
            try {
                numbers.add(Long.parseLong(numberStr));
            } catch (NumberFormatException e) {
                log.error("The string does not have the appropriate format to be parse to long : ", e);
            }
        }

        Collections.sort(numbers);
        return numbers;
    }

    private record SalaryRange(Long minSalary, Long maxSalary){}
}
