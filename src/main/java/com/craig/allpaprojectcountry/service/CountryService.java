package com.craig.allpaprojectcountry.service;

import com.craig.allpaprojectcountry.feignclient.DepartmentFeignClient;
import com.craig.allpaprojectcountry.model.Country;
import com.craig.allpaprojectcountry.model.Department;
import com.craig.allpaprojectcountry.repository.CountryRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
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

//    @Override
//    public List<Country> getAll() {
//        return countryRepository.findAll();
//    }

    @Override
    public Flowable<Country> getAll() {
        return Flowable.fromIterable(countryRepository.findAll())
                .doOnComplete(() -> log.info("All countries have been emitted!"))
                .doOnError(throwable -> log.error("Error occurred while emitting countries", throwable))
                .doOnSubscribe(subscription -> log.info("Subscribed to countries"));
    }


    @Override
    public Maybe<Country> getCountryById(String id) {
        return Maybe.just(Objects.requireNonNull(countryRepository.findById(id).orElse(null)))
                .doOnSuccess(country -> log.info("Country found: {}", country))
                .doOnError(throwable -> log.error("Error finding country: {}", throwable.getMessage()))
                .doOnComplete(() -> log.info("Country not found"))
                .doOnDispose(() -> log.info("Country disposed"));

    }

    @Override
    public Maybe<Country> createCountry(Country country) {
        return Maybe.just(countryRepository.save(country))
                .doOnSuccess(c -> log.info("Country created: {}", c))
                .doOnError(e -> log.error("Error creating country: {}", e.getMessage()))
                .doOnComplete(() -> log.info("Country creation completed"))
                .doOnDispose(() -> log.info("Country creation disposed"));


    }

    @Override
    public Maybe<Country> updateCountry(Country country, String id) {
        return Maybe.just(countryRepository.findById(id))
                .observeOn(Schedulers.io())
                .flatMap(
                        countryOptional -> {
                            if (countryOptional.isPresent()) {
                                Country countryToUpdate = countryOptional.get();
                                countryToUpdate.setName(country.getName());
                                return Maybe.just(countryRepository.save(countryToUpdate));
                            }
                            return Maybe.empty();
                        }
                )
                .doOnSuccess(c -> log.info("Country updated: {}", c))
                .doOnError(e -> log.error("Error updating country: {}", e.getMessage()))
                .doOnComplete(() -> log.info("Country update completed"))
                .doOnDispose(() -> log.info("Country update disposed"));

    }

    @Override
    public Maybe<Country> deleteCountry(String id) {
        return Maybe.just(countryRepository.findById(id))
                .observeOn(Schedulers.io())
                .flatMap(
                        countryOptional -> {
                            if (countryOptional.isPresent()) {
                                Country countryToDelete = countryOptional.get();
                                countryRepository.delete(countryToDelete);
                                return Maybe.just(countryToDelete);
                            }
                            return Maybe.empty();
                        }
                )
                .doOnSuccess(c -> log.info("Country deleted: {}", c))
                .doOnError(e -> log.error("Error deleting country: {}", e.getMessage()))
                .doOnComplete(() -> log.info("Country delete completed"))
                .doOnDispose(() -> log.info("Country delete disposed"));
    }

//    @Override
//    public Flowable<Department> findDepartmentsByCountryIdRx(String countryId) {
//        return countryRepository.findById(countryId).map(country -> getCountryById(countryId, country));
//
//
//    }

    @Override
    public List<Department> findDepartmentsByCountryId(String countryId) {
        return restTemplate.getForObject(DEPARTMENT_SERVICE_URL + countryId, List.class);
    }


    @Override
    public Department saveDepartment(String countryId, Department department) {
        department.setCountryId(countryId);
        return departmentFeignClient.createDepartment(department);
    }

    public Map<String, Object> getCountryWithDepartment(String countryId) {
        Map<String, Object> result = new HashMap<>();
        Country country = countryRepository.findById(countryId).orElse(null);
        if (country == null) {
            result.put("country", "Country not found");
            return result;
        }
        result.put("country", country);
        List<Department> departments = departmentFeignClient.findDepartmentsByCountryId(countryId);
        if (departments == null) {
            result.put("departments", "Departments not found");
            return result;
        }
        result.put("departments", departments);
        return result;

    }
}

