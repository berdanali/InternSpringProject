package com.demo1.demo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class ModelConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
