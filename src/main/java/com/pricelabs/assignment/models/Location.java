package com.pricelabs.assignment.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location{
    public String searchString;
    public double latitude;
    public double longitude;
}
