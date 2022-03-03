package io.vvu.study.java.reactor.demo.datastore.repositories.impl;

import io.vvu.study.java.reactor.demo.datastore.configuration.DatastoreConfig;
import io.vvu.study.java.reactor.demo.datastore.exceptions.NotFoundException;
import io.vvu.study.java.reactor.demo.entities.DeviceEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest( classes = { DatastoreConfig.class, DeviceRepoImpl.class })
class DeviceRepoImplTest {

    private final Logger logger = LoggerFactory.getLogger(DeviceRepoImplTest.class.getSimpleName());

    @Autowired
    private DeviceRepoImpl repo;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    private DeviceEntity createDeviceEntity( String id ) {
        DeviceEntity entity = new DeviceEntity();
        if (Objects.nonNull(id)) entity.setId(id);
        entity.setDeviceType("washing-machine");
        entity.setProductionCode("DX-8000");
        entity.setSerialNumber(UUID.randomUUID().toString());
        entity.setRegistrationDate(LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));

        return entity;
    }

    private DeviceEntity createDeviceEntity() {
        return createDeviceEntity(null);
    }

    @Test
    void testCreateAnDeviceThenFindById_foundMatchedEntity() {

        DeviceEntity entity = createDeviceEntity();

        Mono<DeviceEntity> mono = repo.create(entity).flatMap(id -> repo.findById( id)).log();

        StepVerifier.create(mono).assertNext( e -> {
            assertEquals( entity.getId(), e.getId());
            assertEquals( entity.getRegistrationDate(), e.getRegistrationDate());
            assertEquals( entity.getProductionCode(), e.getProductionCode());
            assertEquals( entity.getSerialNumber(), e.getSerialNumber());
            assertEquals( entity.getDeviceType(), e.getDeviceType());
        }).verifyComplete();

    }

    @Test
   void testFindById_NotFoundEntity_NotFoundException() {
        Mono<DeviceEntity> mono = repo.findById( UUID.randomUUID().toString());
        StepVerifier.create(mono).verifyError(NotFoundException.class);
    }


    @Test
    void testCreateDeviceThenUpdate_thenFindAndVerify_matchUpdated() {

        DeviceEntity entity = createDeviceEntity( UUID.randomUUID().toString());
        DeviceEntity entityForUpdate = new DeviceEntity();

        entityForUpdate.setId(entity.getId());
        entityForUpdate.setSerialNumber(entity.getSerialNumber());
        entityForUpdate.setDeviceType(entity.getDeviceType());
        entityForUpdate.setProductionCode("PANA-WM-XX");
        entityForUpdate.setRegistrationDate(entity.getRegistrationDate());

        Mono<DeviceEntity> mono = repo.create(entity).then(repo.update(entityForUpdate)).then(repo.findById(entity.getId()));

        StepVerifier.create(mono).assertNext( e -> {
            assertEquals( entityForUpdate.getId(), e.getId());
            assertEquals( entityForUpdate.getRegistrationDate(), e.getRegistrationDate());
            assertEquals( entityForUpdate.getProductionCode(), e.getProductionCode());
            assertEquals( entityForUpdate.getSerialNumber(), e.getSerialNumber());
            assertEquals( entityForUpdate.getDeviceType(), e.getDeviceType());
        }).verifyComplete();

    }
}