package com.shubham.employeemanagementsystem.controller;

import com.shubham.employeemanagementsystem.dto.AISearchRequest;
import com.shubham.employeemanagementsystem.dto.EmployeeSearchCriteria;
import com.shubham.employeemanagementsystem.entity.Employee;
import com.shubham.employeemanagementsystem.repository.EmployeeRepository;
import com.shubham.employeemanagementsystem.service.GeminiService;
import com.shubham.employeemanagementsystem.specification.EmployeeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees/search")
public class AISearchController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/ai")
    public ResponseEntity<?> searchWithAI(@RequestBody AISearchRequest request) {
        if (request.getQuery() == null || request.getQuery().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Query text cannot be empty"));
        }

        try {
            EmployeeSearchCriteria criteria = geminiService.parseQueryToCriteria(request.getQuery());
            List<Employee> results = employeeRepository.findAll(EmployeeSpecification.fromCriteria(criteria));
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not process the search query. Please try rephrasing it."));
        }
    }
}