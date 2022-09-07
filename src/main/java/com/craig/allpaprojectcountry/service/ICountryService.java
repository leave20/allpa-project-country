package com.craig.allpaprojectcountry.service;

import com.craig.allpaprojectcountry.model.Country;
import com.craig.allpaprojectcountry.model.Department;

import java.util.List;

public interface ICountryService {

    List<Country> getAll();

    Country getCountryById(String id);

    Country createCountry(Country country);

    Country updateCountry(Country country);

    Country deleteCountry(String id);

    List<Department> findDepartmentsByCountryId(String countryId);

    Department saveDepartment(String countryId,Department department);

}
