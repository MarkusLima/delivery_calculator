package com.challenger.calculator.controllers;

import com.challenger.calculator.models.CalculatorModel;
import com.challenger.calculator.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class ApiCalculatorController {

    @Autowired
    CalculatorService calculatorService;

    @GetMapping("/calculators")
    public ResponseEntity<List<CalculatorModel>> getAllCalculator(){
        return ResponseEntity.status(HttpStatus.OK).body(calculatorService.findAll());
    }
}
