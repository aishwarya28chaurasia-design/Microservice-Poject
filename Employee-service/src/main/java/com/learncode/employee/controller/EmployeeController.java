package com.learncode.employee.controller;

import com.learncode.employee.exception.BadRequestException;
import com.learncode.employee.exception.MissingParameterException;
import com.learncode.employee.model.dto.EmployeeDto;
import com.learncode.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employee Controller", description = "Management APIs for Employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Save a new employee", description = "Creates a new employee record and returns the saved object")
    @PostMapping("/saveEmployee")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto response = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @Operation(summary = "Update employee", description = "Updates an existing employee's details by their ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Long id){
        EmployeeDto response = employeeService.updateEmployee(id,employeeDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "Delete employee", description = "Removes an employee record from the system")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

    @Operation(summary = "Get employee by ID", description = "Returns a single employee object based on the provided long ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id){
        EmployeeDto response = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Operation(summary = "Get all employees", description = "Fetches a list of all employees in the system")
    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(){
        List<EmployeeDto> response = employeeService.getAllEmployee();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Search employee by code and company",
            description = "Fetches an employee using their unique employee code and company name. Throws an error if parameters are missing."
    )
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
