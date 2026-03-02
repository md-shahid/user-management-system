package com.userManagementSystem.controller;

import com.userManagementSystem.payload.EmployeeDto;
import com.userManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/Employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/adduser")
    public ResponseEntity<?>add(@Valid @RequestBody EmployeeDto employeeDto , BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            List<String> list = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        return new ResponseEntity<>(list,HttpStatus.NOT_FOUND);
        }

        EmployeeDto Dto = employeeService.addUser(employeeDto);
        return new ResponseEntity<>(Dto, HttpStatus.CREATED);
    }
    @PostMapping("/addAdmin")
    public ResponseEntity<?>addadmin(@Valid @RequestBody EmployeeDto employeeDto , BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            List<String> list = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return new ResponseEntity<>(list,HttpStatus.NOT_FOUND);
        }

        EmployeeDto Dto = employeeService.addAdmin(employeeDto);
        return new ResponseEntity<>(Dto, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<EmployeeDto>> findAll(){

        List<EmployeeDto> employeeDtos = employeeService.fetchAll();
        return new ResponseEntity<>(employeeDtos,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>update(@Valid@PathVariable Long id,@RequestBody EmployeeDto employeeDto,BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            List<String> list = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return new ResponseEntity<>(list,HttpStatus.NOT_FOUND);
        }
        EmployeeDto employeeDto1 = employeeService.updateById(id, employeeDto);
        return new ResponseEntity<>(employeeDto1,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){

        String s = employeeService.delById(id);
        return new ResponseEntity<>(s,HttpStatus.OK);
    }
    @GetMapping("/email")
    public ResponseEntity<?> byEmail(@RequestParam String email){
        EmployeeDto findemail = employeeService.findemail(email);
        return new ResponseEntity<>(findemail,HttpStatus.OK);
    }
    //http://localhost:8080/?email=

    @GetMapping("/username")
    public ResponseEntity<?> byname(@RequestParam String username){
        EmployeeDto findUsername = employeeService.findUsername(username);
        return new ResponseEntity<>(findUsername,HttpStatus.OK);
    }

}
