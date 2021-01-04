package com.sem.keeper.formatter

import org.springframework.format.Formatter
import java.text.ParseException
import java.time.Duration
import java.util.*

class DurationFormatter : Formatter<Duration> {
    @Throws(ParseException::class)
    override fun parse(text: String, locale: Locale): Duration? {
        return null
    }

    override fun print(`object`: Duration, locale: Locale): String {
        val days = `object`.toDays()
        val hours = `object`.toHoursPart()
        return String.format("%d nap %d óra", days, hours)
    }
}