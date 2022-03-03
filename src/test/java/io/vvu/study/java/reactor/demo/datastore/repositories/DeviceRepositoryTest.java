package io.vvu.study.java.reactor.demo.datastore.repositories;

import io.vvu.study.java.reactor.demo.datastore.configuration.DatastoreConfig;
import io.vvu.study.java.reactor.demo.entities.DeviceEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = DatastoreConfig.class)
class DeviceRepositoryTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    private DeviceRepository repository;

    private DeviceEntity

    createDeviceEntity() {
        DeviceEntity entity = new DeviceEntity();
        entity.setDeviceType("washing-machine");
        entity.setProductionCode("DX-8000");
        entity.setSerialNumber(UUID.randomUUID().toString());
        entity.setRegistrationDate(LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));

        return entity;
    }

    @Test
    void testCreateAnDevice_andNoError() {
        DeviceEntity entity = createDeviceEntity();

        repository.save(entity)
                .log()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testCreateAnDeviceThenFindById_foundMatchedEntity() {

        DeviceEntity entity = createDeviceEntity();

        Mono<DeviceEntity> mono = repository.save(entity).flatMap(e -> repository.findById(e.getId())).log();

        StepVerifier.create(mono).assertNext( e -> {
            System.out.println(e.getId());
            assertEquals( entity.getRegistrationDate(), e.getRegistrationDate());
            assertEquals( entity.getProductionCode(), e.getProductionCode());
            assertEquals( entity.getSerialNumber(), e.getSerialNumber());
            assertEquals( entity.getDeviceType(), e.getDeviceType());
        }).verifyComplete();

    }

}