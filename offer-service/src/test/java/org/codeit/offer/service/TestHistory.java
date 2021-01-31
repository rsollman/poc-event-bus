package org.codeit.offer.service;

import org.codeit.domain.CreateOffer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.Arrays;

@SpringBootTest
public class TestHistory {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testHistory() {
        restTemplate.exchange("http://localhost:9000/kafka/history/create-offer", HttpMethod.POST, getCreateOfferHttpEntity(), CreateOffer.class);
    }

    private HttpEntity<CreateOffer> getCreateOfferHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        CreateOffer createOffer = new CreateOffer("myId", Instant.now(), "myOffer");
        HttpEntity<CreateOffer> entity = new HttpEntity<>(createOffer, headers);
        return entity;
    }
}
