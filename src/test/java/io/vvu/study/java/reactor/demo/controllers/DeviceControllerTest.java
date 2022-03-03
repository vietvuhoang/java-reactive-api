package io.vvu.study.java.reactor.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vvu.study.java.reactor.demo.datastore.exceptions.NotFoundException;
import io.vvu.study.java.reactor.demo.datastore.repositories.inf.DeviceRepo;
import io.vvu.study.java.reactor.demo.dtos.Device;
import io.vvu.study.java.reactor.demo.reactiveapi.ExceptionHandlers;
import io.vvu.study.java.reactor.demo.service.DeviceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ContextConfiguration(classes = {ExceptionHandlers.class, DeviceController.class})
@ExtendWith(SpringExtension.class)
@WebFluxTest
class DeviceControllerTest {

    @Autowired
    private DeviceController controller;

    @MockBean
    private DeviceRepo repository;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @AfterEach
    void tearDown() {
    }

    private Device createDevice() {
        Device device = new Device();
        device.setDeviceType("washing-machine");
        device.setProductionCode("DX-8000");
        device.setSerialNumber(UUID.randomUUID().toString());
        device.setRegistrationDate(LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));

        return device;
    }

    @Test
    void testCreateDevice_success_Http201() {

        Device device = createDevice();

        Mono<String> returnMono = Mono.just(UUID.randomUUID().toString());

        Mockito.when( deviceService.create(any())).thenReturn(returnMono);

        client.post().uri("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(device), Device.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    void testFindDevice_success_Http200() throws JsonProcessingException {

        Device device = createDevice();

        Mono<Device> returnMono = Mono.just(device);

        Mockito.when( deviceService.findById(device.getId())).thenReturn(returnMono);

        client.get().uri(String.format("/devices/%s", device.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json(mapper.writeValueAsString(device));
    }

    @Test
    void testFindDevice_notFound_Http404() throws JsonProcessingException {

        Device device = createDevice();

        Mockito.when( deviceService.findById(device.getId())).thenReturn(Mono.error(NotFoundException::new));

        client.get().uri(String.format("/devices/%s", device.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }
}