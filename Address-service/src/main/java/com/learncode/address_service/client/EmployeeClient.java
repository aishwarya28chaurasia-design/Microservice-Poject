package com.learncode.address_service.client;

import com.learncode.address_service.config.FeignConfig;
import com.learncode.address_service.model.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Employee-Service-Client", url = "${employee.service.url}", configuration = FeignConfig.class)
public interface EmployeeClient {

    @GetMapping("/get/{id}")
    EmployeeDto getEmployeeById(@PathVariable Long id);
}
