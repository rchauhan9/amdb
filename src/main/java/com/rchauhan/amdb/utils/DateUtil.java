package com.rchauhan.amdb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    public static Date createDate(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            LOGGER.error("Could not parse date: {} using format: {}. Returning default of today's date: {}", date, format, new Date());
        }
        return new Date();
    }

}
