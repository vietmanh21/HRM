package com.example.hrm.service;

import com.example.hrm.helper.ExcelHelper;
import com.example.hrm.model.Employee;
import com.example.hrm.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    private final EmployeeRepository employeeRepository;

    public ExcelService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void save(MultipartFile file) {
        try {
            List<Employee> employees = ExcelHelper.excelToTutorials(file.getInputStream());
            employeeRepository.saveAll(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}
