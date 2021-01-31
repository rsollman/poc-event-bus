package org.codeit.consumers;

import org.codeit.domain.CreateOffer;
import org.codeit.producers.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    //@Autowired
    //private RestTemplate restTemplate;

    @KafkaListener(topics = "users", groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
    }

    @KafkaListener(topics = "history", groupId = "history_group", containerFactory = "createOfferKafkaListenerContainerFactory")
    public void consumeHistory(CreateOffer createOffer) throws IOException {
        logger.info("#### -> Consumed createOffer:" + createOffer);

        /*ResponseEntity result = restTemplate.exchange("http://localhost:9090/history/create-offer",
                HttpMethod.POST, getCreateOfferHttpEntity(), CreateOffer.class);

        if (result.getStatusCode() != HttpStatus.OK) {
            logger.info("TODO RETRY");
        }*/
    }

    private HttpEntity<CreateOffer> getCreateOfferHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<CreateOffer> entity = new HttpEntity<>(headers);
        entity.getBody().setId("myId");
        entity.getBody().setCreateTime(Instant.now());
        entity.getBody().setName("myOffer");
        return entity;
    }


}