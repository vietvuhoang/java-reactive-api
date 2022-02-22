package io.vvu.study.java.reactor.demo.service;

import io.vvu.study.java.reactor.demo.datastore.repositories.DeviceRepository;
import io.vvu.study.java.reactor.demo.datastore.repositories.inf.DeviceRepo;
import io.vvu.study.java.reactor.demo.dtos.Device;
import io.vvu.study.java.reactor.demo.entities.DeviceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository repository;

    @Autowired
    private ModelMapper mapper;

    public Mono<String> create(Device device) {
        Device regDevice = device.copy();
        regDevice.setRegistrationDate(LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));
        DeviceEntity deviceEntity = mapper.map(device, DeviceEntity.class);
        return repository.save(deviceEntity).map(DeviceEntity::getId);
    }

    public Mono<Device> findById(String id) {
        return repository.findById(id).map(e -> mapper.map(e, Device.class));
    }
}
