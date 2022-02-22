package io.vvu.study.java.reactor.demo.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vvu.study.java.reactor.demo.reactiveapi.RouterConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@ContextConfiguration(classes = {RouterConfiguration.class, PingHandler.class})
@WebFluxTest
class PingHandlerTest {

    @Autowired
    private WebTestClient client;

    private static final String VERSION = "VER-DEMO-20220228";

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testPing() throws JsonProcessingException {

        client.get().uri("/ping").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json(mapper.writeValueAsString(new PongMessage( VERSION )));

    }
}