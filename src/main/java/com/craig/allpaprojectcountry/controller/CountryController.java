package com.craig.allpaprojectcountry.controller;

import com.craig.allpaprojectcountry.model.Country;
import com.craig.allpaprojectcountry.model.Department;
import com.craig.allpaprojectcountry.service.CountryService;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Unwrapped;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@Api
@Slf4j
@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;


    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

//    @GetMapping("/list")
//    public ResponseEntity<List<Country>> getAll() {
//        List<Country> countries = countryService.getAll();
//        if (countries.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(countries);
//    }

    @GetMapping("/list")
    public Flowable<ResponseEntity<Country>> getAll() {
        return countryService.getAll()
                .map(
                        country -> ResponseEntity.ok()
                                .header(HttpHeaders.DATE, country.getCreatedAt().toString())
                                .body(country)
                )
                .doOnComplete(() -> log.info("All countries have been sent"))
                .onErrorReturn(throwable -> ResponseEntity.badRequest().build());
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<Country> getCountryById(@PathVariable("id") String id) {
//        Country country = countryService.getCountryById(id);
//        if (country == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(country);
//    }

    @GetMapping("/{id}")
    public Maybe<ResponseEntity<Country>> getCountryById(@PathVariable("id") String id) {

        return countryService.getCountryById(id)
                .switchIfEmpty(
                        Maybe.error(new Exception("Country not found"))
                )
                .map(country -> ResponseEntity.ok().body(country)
                )
                .flatMap(countryResponseEntity -> {
                    log.info("Country found");
                    return Maybe.just(countryResponseEntity);
                })
                .toSingle()
                .toMaybe();

    }


//    @GetMapping("/departments/{countryId}")
//    public ResponseEntity<List<Department>> findDepartmentsByCountryId(@PathVariable("countryId") String countryId) {
//        Country country = countryService.getCountryById(countryId);
//
//        if (country == null) {
//            return ResponseEntity.notFound().build();
//        }
//        List<Department> departments = countryService.findDepartmentsByCountryId(countryId);
//        if (departments.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(departments);
//    }


    @PostMapping("/create")
    public Maybe<ResponseEntity<Country>> createCountry(@RequestBody Country country) {
        return countryService.createCountry(country)
                .map(country1 -> ResponseEntity.ok().body(country1))
                .flatMap(countryResponseEntity -> {
                    log.info("Country created");
                    return Maybe.just(countryResponseEntity);
                })
                .toSingle()
                .toMaybe();
    }

//    @PostMapping("/create")
//    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
//        Country newCountry = countryService.createCountry(country);
//        if (newCountry == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(newCountry);
//    }

    @PatchMapping("/update/{id}")
    public Maybe<ResponseEntity<Country>> updateCountry(@RequestBody Country country, @PathVariable("id") String id) {
        return countryService.updateCountry(country, id)
                .map(country1 -> ResponseEntity.ok().body(country1))
                .flatMap(countryResponseEntity -> {
                    log.info("Country updated");
                    return Maybe.just(countryResponseEntity);
                })
                .toSingle()
                .toMaybe();
    }

//    @PatchMapping("/update")
//    public ResponseEntity<Country> updateCountry(@RequestBody Country country) {
//        Country updatedCountry = countryService.updateCountry(country);
//        if (updatedCountry == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(updatedCountry);
//    }



    @DeleteMapping("/delete/{id}")
    public Maybe<ResponseEntity<Country>> deleteCountry(@PathVariable("id") String id) {
        return countryService.deleteCountry(id)
                .map(country1 -> ResponseEntity.ok().body(country1))
                .flatMap(countryResponseEntity -> {
                    log.info("Country deleted");
                    return Maybe.just(countryResponseEntity);
                })
                .toSingle()
                .toMaybe();

    }

    @PostMapping("/addDepartment/{countryId}")
    public ResponseEntity<Department> addDepartment(@PathVariable("countryId") String countryId, @RequestBody Department department) {
        Department createDepartment = countryService.saveDepartment(countryId, department);
        return ResponseEntity.ok(createDepartment);
    }

//    @GetMapping("/getAll/{countryId}")
//    public ResponseEntity<Map<String, Object>> getAll(@PathVariable("countryId") String countryId) {
//        Map<String, Object> result = countryService.getCountryWithDepartment(countryId);
//        return ResponseEntity.ok(result);
//    }

}
