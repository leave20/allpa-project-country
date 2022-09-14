package com.craig.allpaprojectcountry.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "country")
public class Country  {
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
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;



//    private List<Culture> cultures;
}
