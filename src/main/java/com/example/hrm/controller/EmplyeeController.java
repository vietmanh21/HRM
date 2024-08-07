package com.example.hrm.controller;

import com.example.hrm.helper.ExcelHelper;
import com.example.hrm.service.EmployeeService;
import com.example.hrm.service.ExcelService;
import com.example.hrm.viewmodel.EmployeeGetVm;
import com.example.hrm.viewmodel.EmployeePostVm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class EmplyeeController {
    private final EmployeeService employeeService;
    private final ExcelService excelService;

    public EmplyeeController(EmployeeService employeeService, ExcelService excelService) {
        this.employeeService = employeeService;
        this.excelService = excelService;
    }

    @PostMapping("/api/manager/create-employee")
    public ResponseEntity<EmployeeGetVm> createEmployee(@RequestBody EmployeePostVm employeePostVm) {
        return ResponseEntity.ok(employeeService.create(employeePostVm));
    }

    @GetMapping("/api/manager/list-developer")
    public ResponseEntity<List<EmployeeGetVm>> getDeveloper() {
        return ResponseEntity.ok(employeeService.listDeveloper());
    }

    @GetMapping("/api/manager/list-manager")
    public ResponseEntity<List<EmployeeGetVm>> getManager() {
        return ResponseEntity.ok(employeeService.listManager());
    }

    @GetMapping("/api/manager/employee/{userId}")
    public ResponseEntity<EmployeeGetVm> getEmployeeById(@PathVariable(value = "userId") long userId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(userId));
    }

    @PutMapping("/api/manager/employee/{userId}")
    public ResponseEntity<Void> updateEmployee(@PathVariable(value = "userId") long userId, @RequestBody EmployeePostVm employeePostVm) {
        employeeService.update(userId, employeePostVm);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/manager/employee/delete/{userId")
    public ResponseEntity<EmployeeGetVm> deleteEmployee(@PathVariable(value = "userId") long userId) {
        employeeService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/manager/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                excelService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
