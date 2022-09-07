package com.craig.allpaprojectcountry.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "country")
public class Country {
    @Id
    private String id;
    private String name;
    private String location;
    private String limits;
    private String capital;
//    private List<Department> departments;
    private String population;
    private String surface;
    private String language;
    private String history;
//    private List<Culture> cultures;
}
