package com.learncode.employee.client;

import com.learncode.employee.model.dto.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Address-Service")
public interface AddressClient {

    @GetMapping("/addresses/get/{empId}")
   List<AddressDto> getAddressByEmpId(@PathVariable Long empId);
}
