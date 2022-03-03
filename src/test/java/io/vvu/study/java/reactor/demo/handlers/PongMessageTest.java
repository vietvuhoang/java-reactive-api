package io.vvu.study.java.reactor.demo.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PongMessageTest {

    @Test
    void toJson() throws JsonProcessingException {
        PongMessage message = new PongMessage("XX");
        System.out.println(new ObjectMapper().writeValueAsString(message));
    }

}