package com.userManagementSystem.service;

import com.userManagementSystem.entity.Employee;
import com.userManagementSystem.entity.Role;
import com.userManagementSystem.exception.ResourceNotFoundException;
import com.userManagementSystem.payload.EmployeeDto;
import com.userManagementSystem.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

public EmployeeDto addUser(EmployeeDto employeeDto){
    Employee e = modelMapper.map(employeeDto, Employee.class);

    Optional<Employee> byEmail = employeeRepository.findByEmail(employeeDto.getEmail());
    if(byEmail.isPresent()){
        throw new RuntimeException("Invalid email");
    }

    Optional<Employee> byUsername = employeeRepository.findByUsername(employeeDto.getUsername());
    if(byUsername.isPresent()){
     throw new RuntimeException("Username already exists");
    }

    e.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
    e.setRole(Role.USER);
    Employee save = employeeRepository.save(e);
    EmployeeDto map = modelMapper.map(save, EmployeeDto.class);
    return map;
    }


    public EmployeeDto addAdmin(EmployeeDto employeeDto){
        Employee e = modelMapper.map(employeeDto, Employee.class);

        Optional<Employee> byEmail = employeeRepository.findByEmail(employeeDto.getEmail());
        if(byEmail.isPresent()){
            throw new RuntimeException("Invalid email");
        }

        Optional<Employee> byUsername = employeeRepository.findByUsername(employeeDto.getUsername());
        if(byUsername.isPresent()){
            throw new RuntimeException("Username already exists");
        }

        e.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        e.setRole(Role.ADMIN);
        Employee save = employeeRepository.save(e);
        EmployeeDto map = modelMapper.map(save, EmployeeDto.class);
        return map;
    }

    public List<EmployeeDto> fetchAll(){

        List<Employee> all = employeeRepository.findAll();
        List<EmployeeDto> list = all.stream().map(x -> modelMapper.map(x, EmployeeDto.class)).toList();
        return list;
    }

    public EmployeeDto updateById(Long id,EmployeeDto employeeDto){

        employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("id not found"+id));

        Employee map1 = modelMapper.map(employeeDto, Employee.class);

        map1.setId(id);
        map1.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        Employee save = employeeRepository.save(map1);
        EmployeeDto map = modelMapper.map(save, EmployeeDto.class);
        return map;
    }

    public String delById(Long id){
        employeeRepository.deleteById(id);
        return "deleted";
    }

    public EmployeeDto findemail(String email){
        Employee found = employeeRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("email not found"));
        EmployeeDto map = modelMapper.map(found, EmployeeDto.class);
        return map;

    }

    public EmployeeDto findUsername(String username) {

        Employee found = employeeRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("NOT FOUND"));

        return modelMapper.map(found, EmployeeDto.class);

    }
    }
