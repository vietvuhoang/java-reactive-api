package io.vvu.study.java.reactor.demo.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.vvu.study.java.reactor.demo.utilities.jackson.LocalDateTimeDeserializer;
import io.vvu.study.java.reactor.demo.utilities.jackson.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter @Setter
public class Device implements Dto<Device> {
    private String id = UUID.randomUUID().toString();
    private String productionCode;
    private String serialNumber;
    private String deviceType;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime registrationDate;

    @Override
    public Device copy() {
        Device device = new Device();

        device.id = id;
        device.productionCode = productionCode;
        device.serialNumber = serialNumber;
        device.deviceType = deviceType;
        device.registrationDate = registrationDate;

        return device;
    }

}
