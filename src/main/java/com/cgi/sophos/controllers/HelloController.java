package com.cgi.sophos.controllers;

import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@PermitAll
@RequestMapping("hello")
@RestController
public class HelloController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public String getMessage() {
        return "Hello World!!!";
    }
}
