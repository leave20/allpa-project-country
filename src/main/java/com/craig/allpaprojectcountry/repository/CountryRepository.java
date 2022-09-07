package com.craig.allpaprojectcountry.repository;

import com.craig.allpaprojectcountry.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends MongoRepository<Country, String> {



}
