package org.codeit.offer.service;

import org.codeit.domain.CreateOffer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestSomeFlow {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testSomeFlow() throws Exception {

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture
                .thenApply(s -> s + " World");

        assertEquals("Hello World", future.get());

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
