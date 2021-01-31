package org.codeit.controller;

import org.codeit.domain.CreateOffer;
import org.codeit.producers.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final Producer producer;

    @Autowired
    KafkaController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        this.producer.sendMessage(message);
    }

    @PostMapping(value = "/history/create-offer")
    @ResponseBody
    public ResponseEntity sendMessageToKafkaTopic(@RequestBody CreateOffer createOffer) {
        this.producer.sendHistory(createOffer);
        return ResponseEntity.ok().build();

    }
}