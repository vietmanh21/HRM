package com.example.hrm.controller;

import com.example.hrm.service.EmployeeService;
import com.example.hrm.service.JwtService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final EmployeeService employeeService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginController(EmployeeService employeeService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.employeeService = employeeService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        return ResponseEntity.ok(jwtService.createToken(username));
    }

    @PostMapping("/api/resetPassword")
    public ResponseEntity<String> resetPassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password) throws NotFoundException {
        employeeService.updatePassword(password,token);
        return ResponseEntity.ok("Password changed");
    }
}
