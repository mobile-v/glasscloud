package ru.vmsystems.template.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.HttpPutFormContentFilter;

@Configuration
public class FiltersConfig {

    @Bean
    @Order(-1)
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }
}
