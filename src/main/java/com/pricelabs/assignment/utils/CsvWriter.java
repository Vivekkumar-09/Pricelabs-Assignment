package com.pricelabs.assignment.utils;

import com.opencsv.CSVWriter;
import com.pricelabs.assignment.models.ListingDto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public static void writeToCsv(String path, List<ListingDto> listings) throws IOException{
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))){
            String[] header = {"Listing ID", "Listing Title", "Page Name", "Amount Per Stay"};
            writer.writeNext(header);

            for(ListingDto dto: listings){
                writer.writeNext(new String[]{
                        dto.getId(), dto.getTitle(), dto.getPageName(), String.valueOf(dto.getAmountPerStay())
                });
            }
        }
    }
}
