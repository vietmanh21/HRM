package com.example.hrm.service;

import com.example.hrm.exception.NotFoundException;
import com.example.hrm.model.Employee;
import com.example.hrm.model.ResetRequest;
import com.example.hrm.model.Role;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.ResetRequestRepo;
import com.example.hrm.viewmodel.EmployeeGetVm;
import com.example.hrm.viewmodel.EmployeePostVm;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Value("${jwt.secret:123456}")
    private String secretKey;

    private final ResetRequestRepo resetRequestRepo;

    public EmployeeService(EmployeeRepository employeeRepository, ResetRequestRepo resetRequestRepo) {
        this.employeeRepository = employeeRepository;
        this.resetRequestRepo = resetRequestRepo;
    }


    public EmployeeGetVm create(EmployeePostVm employeePostVm) {
        Employee employee = new Employee();
        if (employeePostVm.getRole().toUpperCase().equals("MANAGER")) {
            employee.setRole(Role.MANAGER);
        } else if (employeePostVm.getRole().toUpperCase().equals("DEVELOPER")) {
            employee.setRole(Role.DEVELOPER);
        }
        employee.setUsername(employeePostVm.getUsername());
        employee.setPassword(new BCryptPasswordEncoder().encode(employeePostVm.getPassword()));
        employee.setValidUtil(employeePostVm.getValidUtil());
        employee.setValid(true);
        return EmployeeGetVm.fromModel(employeeRepository.save(employee));
    }

    public List<EmployeeGetVm> listDeveloper() {
        return employeeRepository.findAllDeveloper().stream().map(EmployeeGetVm::fromModel).collect(Collectors.toList());
    }

    public List<EmployeeGetVm> listManager() {
        return employeeRepository.findAllManager().stream().map(EmployeeGetVm::fromModel).collect(Collectors.toList());
    }

    public EmployeeGetVm getEmployeeById(long userId) {
        Employee employee = employeeRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Employee %s is not found", userId)));
        return EmployeeGetVm.fromModel(employee);
    }


    public void update(long userId, EmployeePostVm employeePostVm) {
        Employee employee = employeeRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Employee %s is not found", userId)));
        employee.setUsername(employeePostVm.getUsername());
        employee.setPassword(new BCryptPasswordEncoder().encode(employeePostVm.getPassword()));
        employee.setValidUtil(employeePostVm.getValidUtil());
        employee.setValid(true);
        employeeRepository.save(employee);
    }

    public void delete(long userId) {
        Employee employee = employeeRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Employee %s is not found", userId)));
        employeeRepository.delete(employee);
    }

    public void updatePassword(String password, String token) {
        String username = checkTokenValid(token);
        Employee current_user = employeeRepository.findByUsername(username);
        if (current_user != null) {
            current_user.setPassword(new BCryptPasswordEncoder().encode(password));
            employeeRepository.save(current_user);
        } else {
            throw new NotFoundException("User not found");
        }
    }

    private String checkTokenValid(String token) {
        try {
            String username= Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
            ResetRequest resetRequest =  resetRequestRepo.findByUsername(username);
            if(resetRequest==null){
                System.out.println("Null");
            }
            if(resetRequest!=null) {
                resetRequestRepo.delete(resetRequest);
                return username;
            }
        } catch (Exception ignored) {
            System.out.println("catch");
        }
        return null;
    }
}
