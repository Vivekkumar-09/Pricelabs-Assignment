package com.pricelabs.assignment.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pricelabs.assignment.models.Root;
import com.pricelabs.assignment.services.ListingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ListingController {

    private ListingService listingService;

    // Constructor Injection
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }


    // method take input (address, latitude, longitude, rowsPerPage)
    // call fetchListing to get response from external api.
    @PostMapping
    public String createEntry(@RequestBody Root root) throws JsonProcessingException {

        String address = root.getVariables().getInput().getLocation().searchString;
        double lan = root.getVariables().getInput().getLocation().getLatitude();
        double lon = root.getVariables().getInput().getLocation().getLongitude();
        int rows = root.getVariables().getInput().getPagination().getRowsPerPage();

        System.out.println(address+" "+lan+" "+lon+" "+rows);

        listingService.fetchListing(address, lan, lon, rows);

        return "ok";

    }
}
