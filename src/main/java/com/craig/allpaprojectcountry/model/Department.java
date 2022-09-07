package com.craig.allpaprojectcountry.model;

import lombok.Data;

@Data
public class Department {
    private String name;
    private String location;
    private String limits;
    private String capital;
    private String province;
    private String population;
    private String surface;
    private String history;
    private String countryId;
//    private List<Event> events;
//    private List<Festivity> festivities;
}
