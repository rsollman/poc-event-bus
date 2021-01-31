package org.codeit.consumers;

import org.codeit.domain.CreateOffer;
import org.codeit.producers.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    private RestTemplate restTemplate = new RestTemplate();

    @KafkaListener(topics = "users", groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
    }

    @KafkaListener(topics = "history", groupId = "history_group", containerFactory = "createOfferKafkaListenerContainerFactory")
    public void consumeHistory(CreateOffer createOffer) throws IOException {
        logger.info("#### -> Consumed createOffer:" + createOffer);

        ResponseEntity result = restTemplate.exchange("http://localhost:9090/history/create-offer",
                HttpMethod.POST, getCreateOfferHttpEntity(createOffer), CreateOffer.class);

        if (result.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Triggering retry");
        }
    }

    private HttpEntity<CreateOffer> getCreateOfferHttpEntity(CreateOffer createOffer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<CreateOffer> entity = new HttpEntity<>(createOffer, headers);
        return entity;
    }


}