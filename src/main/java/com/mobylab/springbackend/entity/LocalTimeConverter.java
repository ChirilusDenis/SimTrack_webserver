package com.mobylab.springbackend.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalTime;

@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, java.sql.Time> {

    @Override
    public java.sql.Time convertToDatabaseColumn(LocalTime attribute) {
        if (attribute == null) return null;
        // Preserve milliseconds
        java.sql.Time time = new java.sql.Time(
                attribute.getHour(),
                attribute.getMinute(),
                attribute.getSecond()
        );
        // Add milliseconds back manually
        long millis = time.getTime() + (attribute.getNano() / 1_000_000);
        return new java.sql.Time(millis);
    }

    @Override
    public LocalTime convertToEntityAttribute(java.sql.Time dbData) {
        if (dbData == null) return null;
        // Extract milliseconds from the Time object
        long millis = dbData.getTime() % 1000;
        if (millis < 0) millis += 1000; // safety guard for negative modulo
        return dbData.toLocalTime()
                .withNano((int) (millis * 1_000_000));
    }
}