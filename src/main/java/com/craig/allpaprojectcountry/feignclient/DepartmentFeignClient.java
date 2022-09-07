package com.craig.allpaprojectcountry.feignclient;

import com.craig.allpaprojectcountry.model.Department;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "department-service", url = "http://localhost:8081/department")
public interface DepartmentFeignClient {

    @PostMapping(path="/create")
    Department createDepartment(@RequestBody Department department);

    @GetMapping(path="/country/{countryId}")
    List<Department> findDepartmentsByCountryId(@PathVariable("countryId") String countryId);
}

