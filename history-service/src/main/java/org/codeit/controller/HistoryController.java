package org.codeit.controller;

import org.codeit.domain.CreateOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/history")
public class HistoryController {

    private Logger logger = LoggerFactory.getLogger(HistoryController.class);

    @PostMapping(value = "/create-offer")
    @ResponseBody
    public ResponseEntity createOffer(@RequestBody CreateOffer createOffer) {
        logger.info("Create offer received: " + createOffer);
        return ResponseEntity.ok().build();
    }
}