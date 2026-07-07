package com.shubham.employeemanagementsystem.repository;

import com.shubham.employeemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(String department);
    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> findEmployeesWithSalaryGreaterThan(@Param("salary") Double salary);
    @Query("SELECT e FROM Employee e WHERE e.firstName LIKE %:name%")
    List<Employee> findEmployeesByNameContaining(@Param("name") String name);
    @Query("""
       SELECT e
       FROM Employee e
       WHERE e.department = :department
       AND e.salary >= :minSalary
       """)
    Page<Employee> findByDepartmentAndSalaryGreaterThanEqual(
            @Param("department") String department,
            @Param("minSalary") Double minSalary,
            Pageable pageable);
}
