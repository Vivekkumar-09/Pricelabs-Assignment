package com.pricelabs.assignment.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListingDto {

    private long id;
    private String title;
    private String pageName;
    private double amountPerStay;
}
