package com.sem.keeper.formatter

import org.springframework.format.Formatter
import java.text.ParseException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class LocalDateTimeFormatter : Formatter<LocalDateTime> {
    @Throws(ParseException::class)
    override fun parse(text: String, locale: Locale): LocalDateTime {
        return LocalDateTime.parse(text, formatter)
    }

    override fun print(`object`: LocalDateTime, locale: Locale): String {
        return `object`.format(formatter)
    }

    companion object {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    }
}
