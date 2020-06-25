package com.sem.keeper.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.Duration;
import java.util.Locale;

public class DurationFormatter implements Formatter<Duration> {

    @Override
    public Duration parse(String text, Locale locale) throws ParseException {
        return null;
    }

    @Override
    public String print(Duration object, Locale locale) {
        long days = object.toDays();
        int hours = object.toHoursPart();
        return String.format("%d nap %d óra", days, hours);
    }
}
