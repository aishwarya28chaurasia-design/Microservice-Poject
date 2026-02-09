package com.learncode.employee.service.impl;

import com.learncode.employee.exception.BadRequestException;
import com.learncode.employee.exception.ResourceNotFoundException;
import com.learncode.employee.model.dto.EmployeeDto;
import com.learncode.employee.model.entity.Employee;
import com.learncode.employee.repository.EmployeeRepository;
import com.learncode.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        if(employeeDto.getId() != null){
            throw new RuntimeException("Employee already exist");
        }
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEntity = employeeRepository.save(employee);

        return modelMapper.map(savedEntity, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if(id == null || employeeDto.getId() == null){
            throw new BadRequestException("Please provide the id");
        }
        if(!Objects.equals(id,employeeDto.getId())){
            throw new BadRequestException("Id mismatch");
        }
        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        Employee employee = modelMapper.map(employeeDto,Employee.class);
        Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee,EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found with id: " +id));
        employeeRepository.delete(employee);

    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
         Employee getEmployee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found with id: " +id));
         return modelMapper.map(getEmployee,EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> getAllEmployee = employeeRepository.findAll();
        if(getAllEmployee.isEmpty()){
            throw new ResourceNotFoundException("No employee found");
        }
        return getAllEmployee.stream().map(emp -> modelMapper.map(emp, EmployeeDto.class)).toList();
    }

    @Override
    public EmployeeDto getEmployeeCodeAndCompanyName(String empCode, String companyName) {
       Employee response =  employeeRepository.findByEmpCodeAndCompanyName(empCode, companyName).orElseThrow(() ->
                new ResourceNotFoundException("Employee not found with empCode: " +empCode +" and companyName: " +companyName));

       return modelMapper.map(response,EmployeeDto.class);
    }
}
