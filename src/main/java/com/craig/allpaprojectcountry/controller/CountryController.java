package com.craig.allpaprojectcountry.controller;

import com.craig.allpaprojectcountry.model.Country;
import com.craig.allpaprojectcountry.model.Department;
import com.craig.allpaprojectcountry.service.CountryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Api
@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;


    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Country>> getAll() {
        List<Country> countries = countryService.getAll();
        if (countries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") String id) {
        Country country = countryService.getCountryById(id);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(country);
    }

    @GetMapping("/departments/{countryId}")
    public ResponseEntity<List<Department>> findDepartmentsByCountryId(@PathVariable("countryId") String countryId) {
        Country country = countryService.getCountryById(countryId);

        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        List<Department> departments = countryService.findDepartmentsByCountryId(countryId);
        if (departments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(departments);
    }

    @PostMapping("/create")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        Country newCountry = countryService.createCountry(country);
        if (newCountry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newCountry);
    }

    @PatchMapping("/update")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) {
        Country updatedCountry = countryService.updateCountry(country);
        if (updatedCountry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCountry);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable("id") String id) {
        Country deletedCountry = countryService.deleteCountry(id);
        if (deletedCountry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedCountry);
    }

    @PostMapping("/addDepartment/{countryId}")
    public ResponseEntity<Department> addDepartment(@PathVariable("countryId") String countryId, @RequestBody Department department) {
        Department createDepartment = countryService.saveDepartment(countryId, department);
        return ResponseEntity.ok(createDepartment);
    }

    @GetMapping("/getAll/{countryId}")
    public ResponseEntity<Map<String, Object>> getAll(@PathVariable("countryId") String countryId) {
        Map<String, Object> result = countryService.getCountryWithDepartment(countryId);
        return ResponseEntity.ok(result);
    }

}
