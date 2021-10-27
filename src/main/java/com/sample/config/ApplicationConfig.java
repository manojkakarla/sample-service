package com.sample.config;

import com.sample.util.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.sample"})
@PropertySource(value = "classpath:application.yaml")
public class ApplicationConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return JacksonUtils.commonObjectMapper();
  }

}
