package com.sem.keeper.formatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FormatterConfig implements WebMvcConfigurer {

    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateTimeFormatter());
        registry.addFormatter(new DurationFormatter());
    }
}
