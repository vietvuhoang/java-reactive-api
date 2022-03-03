package io.vvu.study.java.reactor.demo.reactiveapi;

import io.vvu.study.java.reactor.demo.handlers.PingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {

    @Autowired
    private PingHandler handler;

    @Bean
    public RouterFunction<ServerResponse> handlePing() {
        return route().GET("/ping", accept(APPLICATION_JSON), handler::ping).build();
    }

}
