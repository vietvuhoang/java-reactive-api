package io.vvu.study.java.reactor.demo.datastore.repositories.impl;

import io.vvu.study.java.reactor.demo.datastore.exceptions.NotFoundException;
import io.vvu.study.java.reactor.demo.datastore.repositories.inf.DeviceRepo;
import io.vvu.study.java.reactor.demo.entities.DeviceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Repository
public class DeviceRepoImpl implements DeviceRepo {

    private static final String TABLE_NAME = "devices";

    private final Logger logger = LoggerFactory.getLogger(DeviceRepoImpl.class.getSimpleName());

    @Autowired
    private R2dbcEntityTemplate template;

    @Override
    public Flux<DeviceEntity> getDevices() {
        return template
                .select(DeviceEntity.class)
                .from(TABLE_NAME).all();
    }

    @Override
    public Mono<DeviceEntity> findById(String id) {
        return template
                .select(DeviceEntity.class)
                .from(TABLE_NAME)
                .matching(Query.query(Criteria.from(Criteria.where("id").is(id))))
                .one()
                .switchIfEmpty(Mono.error(new NotFoundException("Device Not found : " + id)));
    }

    @Override
    public Mono<String> create(DeviceEntity deviceEntity) {
        if (Objects.isNull(deviceEntity.getId())) deviceEntity.setId(UUID.randomUUID().toString());
        return template.insert(DeviceEntity.class).into(TABLE_NAME).using(deviceEntity).map(DeviceEntity::getId);
    }

    @Override
    public Mono<Void> update(DeviceEntity deviceEntity) {
        return template
                .update(DeviceEntity.class)
                .inTable(TABLE_NAME)
                .matching(
                        Query.query(Criteria.where("id").is(deviceEntity.getId())))
                .apply(
                        Update.update("device_type", deviceEntity.getDeviceType())
                                .set("production_code", deviceEntity.getProductionCode())
                ).thenEmpty(Mono.empty());
    }

}
