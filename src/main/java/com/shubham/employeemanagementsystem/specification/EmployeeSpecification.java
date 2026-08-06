package com.shubham.employeemanagementsystem.specification;

import com.shubham.employeemanagementsystem.dto.EmployeeSearchCriteria;
import com.shubham.employeemanagementsystem.entity.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> fromCriteria(EmployeeSearchCriteria c) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (c.getDepartment() != null) {
                predicates.add(cb.equal(cb.lower(root.get("department")), c.getDepartment().toLowerCase()));
            }

            if (c.getFirstName() != null) {
                predicates.add(cb.equal(cb.lower(root.get("firstName")), c.getFirstName().toLowerCase()));
            }

            if (c.getLastName() != null) {
                predicates.add(cb.equal(cb.lower(root.get("lastName")), c.getLastName().toLowerCase()));
            }

            if (c.getMinSalary() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("salary"), c.getMinSalary()));
            }

            if (c.getMaxSalary() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("salary"), c.getMaxSalary()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}