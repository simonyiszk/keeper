package com.sem.keeper.formatter;

import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Saját dátumformátum, mert a default-tól kifolyt a szemem
 */
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    /**
     * Az a dátum- és időformátum amit minden értelmes ember használ
     */
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, formatter);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.format(formatter);
    }
}
