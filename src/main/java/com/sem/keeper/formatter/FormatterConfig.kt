package com.sem.keeper.formatter

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class FormatterConfig : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addFormatter(LocalDateTimeFormatter())
        registry.addFormatter(DurationFormatter())
    }
}