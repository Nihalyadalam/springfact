package com.example.snowden.snowden.controller;

import com.example.snowden.snowden.models.Fact;
import com.example.snowden.snowden.service.FactService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FactController {
    
@PostMapping("/facts")
ResponseEntity<Fact> getfacts(@RequestBody Fact fact) {
        Logger logger = LoggerFactory.getLogger(FactController.class);
        logger.info(fact.getFact());
        Fact resultfact = FactService.preprocessFact(fact.getId(), fact.getFact());
        return new ResponseEntity<Fact>(resultfact, HttpStatus.OK);
    }
}
