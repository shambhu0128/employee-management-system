package com.shubham.employeemanagementsystem.controller;


import com.shubham.employeemanagementsystem.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import com.shubham.employeemanagementsystem.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to Employee Management System";
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto) {

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping("/employees")
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    @GetMapping("/employees/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    @PutMapping("/employees/{id}")
    public EmployeeDto updateEmployee(@PathVariable Long id,
                                      @Valid @RequestBody EmployeeDto updatedEmployeeDto) {

        return employeeService.updateEmployee(id, updatedEmployeeDto);
    }
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }
    @GetMapping("/employees/page")
    public Page<EmployeeDto> getEmployees(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeService.getEmployees(pageable);
    }
    @GetMapping("/employees/filter")
    public Page<EmployeeDto> filterEmployees(

            @RequestParam String department,

            @RequestParam Double minSalary,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeService.filterEmployees(department, minSalary, pageable);
    }
    @GetMapping("/employees/department/{department}")
    public List<EmployeeDto> getEmployeesByDepartment(@PathVariable String department) {

        return employeeService.getEmployeesByDepartment(department);
    }
    @GetMapping("/employees/search/{name}")
    public List<EmployeeDto> searchEmployeesByName(@PathVariable String name) {

        return employeeService.searchEmployeesByName(name);
    }
    @GetMapping("/employees/high-salary/{salary}")
    public List<EmployeeDto> getEmployeesWithSalaryGreaterThan(@PathVariable Double salary) {

        return employeeService.getEmployeesWithSalaryGreaterThan(salary);
    }


}