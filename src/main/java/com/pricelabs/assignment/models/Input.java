package com.pricelabs.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Input{
    public Location location;
    public Pagination pagination;
    public boolean useSearchParamsFromSession;

}
