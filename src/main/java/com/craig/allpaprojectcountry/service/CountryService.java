package com.craig.allpaprojectcountry.service;

import com.craig.allpaprojectcountry.feignclient.DepartmentFeignClient;
import com.craig.allpaprojectcountry.model.Country;
import com.craig.allpaprojectcountry.model.Department;
import com.craig.allpaprojectcountry.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CountryService implements ICountryService {

    private static final String DEPARTMENT_SERVICE_URL = "http://localhost:8081/department/country/";

    private final CountryRepository countryRepository;

    private final RestTemplate restTemplate;

    private final DepartmentFeignClient departmentFeignClient;

    @Autowired
    public CountryService(CountryRepository countryRepository, RestTemplate restTemplate, DepartmentFeignClient departmentFeignClient) {
        this.countryRepository = countryRepository;
        this.restTemplate = restTemplate;
        this.departmentFeignClient = departmentFeignClient;
    }

    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountryById(String id) {
        return countryRepository.findById(id).orElse(null);
    }

    @Override
    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country updateCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country deleteCountry(String id) {
        return countryRepository.findById(id).map(country -> {
            countryRepository.delete(country);
            return country;
        }).orElse(null);
    }

    @Override
    public List<Department> findDepartmentsByCountryId(String countryId) {
        return restTemplate.getForObject(DEPARTMENT_SERVICE_URL + countryId, List.class);
    }

    @Override
    public Department saveDepartment(String countryId,Department department) {
        department.setCountryId(countryId);
        return departmentFeignClient.createDepartment(department);
    }

    public Map<String,Object> getCountryWithDepartment(String countryId){
        Map<String,Object> result = new HashMap<>();
        Country country = countryRepository.findById(countryId).orElse(null);
        if (country == null){
            result.put("country","Country not found");
            return result;
        }
        result.put("country",country);
        List<Department> departments = departmentFeignClient.findDepartmentsByCountryId(countryId);
        if (departments == null){
            result.put("departments","Departments not found");
            return result;
        }
        result.put("departments",departments);
        return result;

    }
}

