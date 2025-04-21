package com.pricelabs.assignment.controllers;


import com.pricelabs.assignment.models.Root;
import com.pricelabs.assignment.services.ListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ListingController {

    private ListingService listingService;

    private Logger logger = LoggerFactory.getLogger(ListingController.class);

    // Constructor Injection
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }


    // method take input (address, latitude, longitude, rowsPerPage)
    // call fetchListing to get response from external api.
    @PostMapping
    public String createEntry(@RequestBody Root root) throws IOException {

        String address = root.getVariables().getInput().getLocation().searchString;
        double lan = root.getVariables().getInput().getLocation().getLatitude();
        double lon = root.getVariables().getInput().getLocation().getLongitude();
        int rows = root.getVariables().getInput().getPagination().getRowsPerPage();

        logger.info(String.format("%s %f %f %d", address, lan, lon, rows));

        listingService.fetchListing(address, lan, lon, rows);

        return "ok";

    }
}
