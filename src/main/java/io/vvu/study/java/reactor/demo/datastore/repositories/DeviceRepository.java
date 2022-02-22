package io.vvu.study.java.reactor.demo.datastore.repositories;

import io.vvu.study.java.reactor.demo.entities.DeviceEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

public interface DeviceRepository extends ReactiveCrudRepository<DeviceEntity, String> {
}
