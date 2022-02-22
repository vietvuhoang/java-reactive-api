package io.vvu.study.java.reactor.demo.handlers;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
class PongMessage {

    private static final String PONG_MESSAGE = "pong";

    private final String message;
    private final String version;

    public PongMessage(String version) {
        this.version = version;
        this.message = PONG_MESSAGE;
    }

}
