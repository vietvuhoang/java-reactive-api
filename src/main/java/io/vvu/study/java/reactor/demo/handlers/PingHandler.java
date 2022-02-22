package io.vvu.study.java.reactor.demo.handlers;

import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2CodecSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class PingHandler {

    private static final String VERSION = "VER-DEMO-20220228";

    public Mono<ServerResponse> ping(ServerRequest serverRequest) {
        return ok().contentType(APPLICATION_JSON).bodyValue(new PongMessage(VERSION));
    }
}
