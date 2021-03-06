package org.codeit.producers;

import org.codeit.domain.CreateOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "users";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, CreateOffer> createOfferKafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }

    public ListenableFuture<SendResult<String, CreateOffer>> sendHistory(CreateOffer createOffer) {
        logger.info("#### -> Producing createOffer: " + createOffer);
        return createOfferKafkaTemplate.send("history", createOffer);
    }
}