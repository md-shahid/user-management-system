package com.userManagementSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {


    @GetMapping(path = "/test1")
    public ResponseEntity<String> get()
    {
        return new ResponseEntity<>("HELLO AISHA", HttpStatus.OK);
    }

    @GetMapping(path = "/test2")
    public ResponseEntity<String> gets()
    {
        return new ResponseEntity<>("HELLO FARDEEN", HttpStatus.OK);
    }


}
