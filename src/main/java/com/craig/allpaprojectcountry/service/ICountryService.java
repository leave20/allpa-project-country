package com.craig.allpaprojectcountry.service;

import com.craig.allpaprojectcountry.model.Country;
import com.craig.allpaprojectcountry.model.Department;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;

public interface ICountryService {

//    List<Country> getAll();

    Flowable<Country> getAll();


//    Country getCountryById(String id);

    Maybe<Country> getCountryById(String id);

//    Country createCountry(Country country);

    Maybe<Country> createCountry(Country country);

//    Country updateCountry(Country country);

    Maybe<Country> updateCountry(Country country, String id);

    Maybe<Country> deleteCountry(String id);

    List<Department> findDepartmentsByCountryId(String countryId);

//    Flowable<Department> findDepartmentsByCountryId(String countryId);

    Department saveDepartment(String countryId,Department department);

}
