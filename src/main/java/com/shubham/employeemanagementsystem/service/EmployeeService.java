package com.shubham.employeemanagementsystem.service;

import com.shubham.employeemanagementsystem.dto.EmployeeDto;
import com.shubham.employeemanagementsystem.entity.Employee;
import com.shubham.employeemanagementsystem.event.EmployeeCreatedEvent;
import com.shubham.employeemanagementsystem.event.EmployeeEventProducer;
import com.shubham.employeemanagementsystem.exception.ResourceNotFoundException;
import com.shubham.employeemanagementsystem.mapper.EmployeeMapper;
import com.shubham.employeemanagementsystem.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final EmployeeEventProducer employeeEventProducer;

    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeEventProducer employeeEventProducer) {
        this.employeeRepository = employeeRepository;
        this.employeeEventProducer = employeeEventProducer;
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        logger.info("Saving employee: {}", employeeDto.getFirstName());

        Employee employee = EmployeeMapper.toEntity(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        logger.info("Employee saved successfully with ID: {}", savedEmployee.getId());

        EmployeeCreatedEvent event = new EmployeeCreatedEvent(
                savedEmployee.getId(),
                savedEmployee.getFirstName() + " " + savedEmployee.getLastName(),
                savedEmployee.getEmail(),
                LocalDateTime.now()
        );
        employeeEventProducer.publishEmployeeCreated(event);

        return EmployeeMapper.toDto(savedEmployee);
    }

    public List<EmployeeDto> getAllEmployees() {

        logger.info("Fetching all employees");

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }
    @Cacheable(value = "employees", key = "#id")
    public EmployeeDto getEmployeeById(Long id) {

        logger.info("Fetching employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with id " + id);
                });

        return EmployeeMapper.toDto(employee);
    }
    @CacheEvict(value = "employees", key = "#id")
    public EmployeeDto updateEmployee(Long id, EmployeeDto updatedEmployeeDto) {

        logger.info("Updating employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with id " + id);
                });

        employee.setFirstName(updatedEmployeeDto.getFirstName());
        employee.setLastName(updatedEmployeeDto.getLastName());
        employee.setEmail(updatedEmployeeDto.getEmail());
        employee.setDepartment(updatedEmployeeDto.getDepartment());
        employee.setSalary(updatedEmployeeDto.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);

        logger.info("Employee updated successfully with ID: {}", id);

        return EmployeeMapper.toDto(updatedEmployee);
    }
    @CacheEvict(value = "employees", key = "#id")
    public String deleteEmployee(Long id) {

        logger.info("Deleting employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with id " + id);
                });

        employeeRepository.delete(employee);

        logger.info("Employee deleted successfully with ID: {}", id);

        return "Employee deleted successfully";
    }

    public Page<EmployeeDto> getEmployees(Pageable pageable) {

        logger.info("Fetching employees with pagination");

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        return employeePage.map(EmployeeMapper::toDto);
    }

    public List<EmployeeDto> getEmployeesByDepartment(String department) {

        logger.info("Fetching employees from department: {}", department);

        List<Employee> employees = employeeRepository.findByDepartment(department);

        return employees.stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public List<EmployeeDto> getEmployeesWithSalaryGreaterThan(Double salary) {

        logger.info("Fetching employees with salary greater than: {}", salary);

        List<Employee> employees =
                employeeRepository.findEmployeesWithSalaryGreaterThan(salary);

        return employees.stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public List<EmployeeDto> searchEmployeesByName(String name) {

        logger.info("Searching employees with name: {}", name);

        List<Employee> employees =
                employeeRepository.findEmployeesByNameContaining(name);

        return employees.stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public Page<EmployeeDto> filterEmployees(String department,
                                             Double minSalary,
                                             Pageable pageable) {

        logger.info("Filtering employees. Department: {}, Minimum Salary: {}",
                department, minSalary);

        Page<Employee> employeePage =
                employeeRepository.findByDepartmentAndSalaryGreaterThanEqual(
                        department,
                        minSalary,
                        pageable
                );

        return employeePage.map(EmployeeMapper::toDto);
    }
}