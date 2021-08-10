package com.example.snowden.controller;


import java.util.ArrayList;
import java.util.List;

import com.example.snowden.snowden.Main;
import com.example.snowden.snowden.models.Fact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FactController {
    
@RequestMapping(value = "/facts", method = RequestMethod.POST)
ResponseEntity<List<Fact>> getfacts(@RequestBody List<Fact> facts) {
        Logger logger = LoggerFactory.getLogger(FactController.class);
        logger.info(facts.get(0).getFact());
        List<Fact> resultFact = new ArrayList<>();
        for (Fact fact: facts) {
            Fact resultfact = Main.preprocessFact(fact.getId(), fact.getFact());
            resultFact.add(resultfact);
        }
        return new ResponseEntity<List<Fact>>(resultFact, HttpStatus.OK);
}

}
