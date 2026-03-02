package com.userManagementSystem.controller;

import com.userManagementSystem.entity.Employee;
import com.userManagementSystem.exception.ResourceNotFoundException;
import com.userManagementSystem.payload.LoginDto;
import com.userManagementSystem.repository.EmployeeRepository;
import com.userManagementSystem.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public AuthController(EmployeeRepository employeeRepository, JwtService jwtService) {
        this.employeeRepository = employeeRepository;
        this.jwtService = jwtService;
    }

    @PostMapping(path ="/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto)
    {
        Employee employee = employeeRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new ResourceNotFoundException("USERNAME IS NOT FOUND" + loginDto.getUsername()));
        if (BCrypt.checkpw(loginDto.getPassword(),employee.getPassword()))
        {
            String s = jwtService.generateToken(employee);
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("PASSWORD IS INVALID",HttpStatus.BAD_REQUEST);
        }
    }


}
