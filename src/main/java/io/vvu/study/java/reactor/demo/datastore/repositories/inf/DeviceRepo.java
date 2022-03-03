package io.vvu.study.java.reactor.demo.datastore.repositories.inf;

import io.vvu.study.java.reactor.demo.entities.DeviceEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeviceRepo {
    Flux<DeviceEntity> getDevices();

    Mono<DeviceEntity> findById(String id);

    Mono<String> create(DeviceEntity deviceEntity);

    Mono<Void> update(DeviceEntity deviceEntity);
}
