package com.learncode.employee.controller;

import com.learncode.employee.exception.BadRequestException;
import com.learncode.employee.exception.MissingParameterException;
import com.learncode.employee.model.dto.EmployeeDto;
import com.learncode.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/saveEmployee")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto response = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Long id){
        EmployeeDto response = employeeService.updateEmployee(id,employeeDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id){
        EmployeeDto response = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(){
        List<EmployeeDto> response = employeeService.getAllEmployee();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getByCodeAndName")
    public ResponseEntity<EmployeeDto> getEmployeeCodeAndCompanyName(@RequestParam(required = false) String empCode,
                                                                     @RequestParam(required = false) String companyName){

        List<String> missingParam = new ArrayList<>();
        if(empCode == null || empCode.trim().isEmpty()){
            missingParam.add("empCode");
        }
        if(companyName == null || companyName.trim().isEmpty()){
            missingParam.add("companyName");
        }
        if(!missingParam.isEmpty()){
            String finalMessage = missingParam.stream().collect(Collectors.joining(","));
            throw new MissingParameterException("Please provide: " +finalMessage);
        }
        EmployeeDto response = employeeService.getEmployeeCodeAndCompanyName(empCode,companyName);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
