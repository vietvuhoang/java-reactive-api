package io.vvu.study.java.reactor.demo.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vvu.study.java.reactor.demo.dtos.Device;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeviceEntityTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testMapperEntityToDto() {

        ModelMapper mapper = new ModelMapper();

        DeviceEntity entity = new DeviceEntity();
        entity.setDeviceType("washing-machine");
        entity.setProductionCode("DX-8000");
        entity.setSerialNumber(UUID.randomUUID().toString());
        entity.setRegistrationDate(LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));

        Device device = mapper.map( entity, Device.class);

        assertEquals( entity.getId(), device.getId());
        assertEquals( entity.getRegistrationDate(), device.getRegistrationDate());
        assertEquals( entity.getProductionCode(), device.getProductionCode());
        assertEquals( entity.getSerialNumber(), device.getSerialNumber());
        assertEquals( entity.getDeviceType(), device.getDeviceType());

    }

    @Test
    void testMapVerify() {

        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap( DeviceEntity.class, Device.class).validate();

    }

    @Test
    void toJson() throws JsonProcessingException {

        Device device = new Device();
        device.setDeviceType("washing-machine");
        device.setProductionCode("DX-8000");
        device.setSerialNumber(UUID.randomUUID().toString());
        device.setRegistrationDate(LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));

        String jsonS = new ObjectMapper().writeValueAsString(device);

        System.out.println( jsonS );

        Device deviceJson = new ObjectMapper().readValue(jsonS, Device.class);

        assertEquals( device.getId(), deviceJson.getId());
        assertEquals( device.getDeviceType(), deviceJson.getDeviceType());
        assertEquals( device.getProductionCode(), deviceJson.getProductionCode());
        assertEquals( device.getSerialNumber(), deviceJson.getSerialNumber());
        assertEquals( device.getRegistrationDate(), deviceJson.getRegistrationDate());

    }

    @Test
    void testDtoToEntity() {

        ModelMapper mapper = new ModelMapper();

        Device device = new Device();
        device.setDeviceType("washing-machine");
        device.setProductionCode("DX-8000");
        device.setSerialNumber(UUID.randomUUID().toString());
        device.setRegistrationDate(LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));

        DeviceEntity entity = mapper.map( device, DeviceEntity.class);

        assertEquals( entity.getId(), device.getId());
        assertEquals( entity.getRegistrationDate(), device.getRegistrationDate());
        assertEquals( entity.getProductionCode(), device.getProductionCode());
        assertEquals( entity.getSerialNumber(), device.getSerialNumber());
        assertEquals( entity.getDeviceType(), device.getDeviceType());

    }
}