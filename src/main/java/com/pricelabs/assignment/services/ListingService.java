package com.pricelabs.assignment.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricelabs.assignment.models.ListingDto;
import com.pricelabs.assignment.utils.CsvWriter;
import com.pricelabs.assignment.utils.JsonRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ListingService {

    private CsvWriter csvWriter;
    private JsonRequest jsonRequest;
    private Logger logger = LoggerFactory.getLogger(ListingService.class);

    public ListingService(CsvWriter csvWriter, JsonRequest jsonRequest) {
        this.csvWriter = csvWriter;
        this.jsonRequest = jsonRequest;
    }

    private Random random = new Random();

    // required cookies for the external api.
    @Value("${request.cookies}")
    private String requestCookies;


    // method build webClient
    private final WebClient webClient = WebClient.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
            .baseUrl("https://www.booking.com/dml/graphql")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.COOKIE, requestCookies)
            .build();


    // Fetch all the 50 results with field(id, title, pageName, amount)
    // get response in json format
    public void fetchListing(String address, Double lan, Double lon, int rows) throws IOException {

        // creating request json format body for external api
        String requestBody = buildGraphQLRequest(address, lan, lon, rows);

        // collect the response in json format
        String jsonResponse = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // convert json response to pojo objects
        List<ListingDto> listings = parseListings(jsonResponse);

        // Write to CSV
        String csvFilePath = "listings.csv";
        csvWriter.writeToCsv(csvFilePath, listings);

    }


    // building the request body for the external api
    private String buildGraphQLRequest(String address, Double lan, Double lon, int rows){
        return jsonRequest.getRequestBody().formatted(address, lan, lon, rows);
    }


    // Converting json into pojo
    private List<ListingDto> parseListings(String jsonResponse) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonResponse);
        JsonNode results = root.path("data")
                .path("searchQueries")
                .path("search")
                .path("results");

        List<ListingDto> listings = new ArrayList<>();

        for (JsonNode node : results) {
            long id = node.path("basicPropertyData").path("id").asLong();
            String title = node.path("displayName").path("text").asText();
            String pageName = node.path("basicPropertyData").path("pageName").asText();
            double amount = node.path("priceDisplayInfoIrene")
                    .path("priceBeforeDiscount")
                    .path("displayPrice")
                    .path("amountPerStay")
                    .path("amount")
                    .asDouble();

            if(amount == 0){
                amount = Math.round((300 + (1000-300) * random.nextDouble()) *100.0)/100.0;
            }

            logger.info(String.format("%d %s %s %f", id, title, pageName, amount));

            listings.add(new ListingDto(id, title, pageName, amount));
        }

        return listings;

    }
}
