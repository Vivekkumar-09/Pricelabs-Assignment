package com.pricelabs.assignment.utils;

import com.opencsv.CSVWriter;
import com.pricelabs.assignment.models.ListingDto;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CsvWriter {

    public void writeToCsv(String path, List<ListingDto> listings) throws IOException{
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))){
            String[] header = {"Listing ID", "Listing Title", "Page Name", "Amount Per Stay"};
            writer.writeNext(header);

            for(ListingDto dto: listings){
                writer.writeNext(new String[]{
                        String.valueOf(dto.getId()), dto.getTitle(), dto.getPageName(), String.valueOf(dto.getAmountPerStay())
                });
            }
        }
    }
}
