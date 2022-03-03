package io.vvu.study.java.reactor.demo.controllers;

import io.vvu.study.java.reactor.demo.dtos.Device;
import io.vvu.study.java.reactor.demo.service.DeviceService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@Getter @Setter
class DeviceResponse {
    private String id;
}

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DeviceResponse> create(@RequestBody Device device) {
        return service
                .create(device)
                .map(DeviceResponse::new);
    }

    @GetMapping("/{id}")
    public Mono<Device> get(@PathVariable String id) {
        return service.findById(id);
    }

}
