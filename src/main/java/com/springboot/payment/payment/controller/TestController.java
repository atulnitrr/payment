package com.springboot.payment.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping
    public ResponseEntity<?> getString() {
        return ResponseEntity.ok().body("Test controller");
    }
}
