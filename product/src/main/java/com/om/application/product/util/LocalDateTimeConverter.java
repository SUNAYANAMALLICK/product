package com.om.application.product.util;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter<T> extends AbstractBeanField<T, LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) throws CsvDataTypeMismatchException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(s, formatter);
    }

    @Override
    public void setType(Class<?> aClass) {
        // No need to implement
    }

    @Override
    public Class<? extends LocalDateTime> getType() {
        return LocalDateTime.class;
    }
}
