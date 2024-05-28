package com.om.application.product.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {

    public static LocalDateTime parseIso8601DateTime(String isoDateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoDateTime);
        return zonedDateTime.toLocalDateTime();
    }
}

