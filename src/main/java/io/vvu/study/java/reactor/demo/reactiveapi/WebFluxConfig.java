package io.vvu.study.java.reactor.demo.reactiveapi;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Bean
    public ModelMapper provideModelMapper() {
        return new ModelMapper();
    }
}
