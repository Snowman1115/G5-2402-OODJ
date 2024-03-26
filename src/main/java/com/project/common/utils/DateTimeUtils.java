/**
 * Utils - Date N Timer Formatter
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-26
 * @since 2024-03-26
 */

package com.project.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static DateTimeFormatter df = DateTimeFormatter.ofPattern(PropertiesReader.getProperty("DateFormat"));
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PropertiesReader.getProperty("DateTimeFormat"));

    /**
     * Parse String Date To LocalDate
     * @param date
     * @return Formatted date
     */
    public static LocalDate formatDate(String date) {
        return LocalDate.parse(date);
    }

    /**
     * Parse String Date N Time To LocalDateTime
     * @param dateTime
     * @return Formatted datetime
     */
    public static LocalDateTime formatDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime,dtf);
    }

    /**
     * Parse LocalDate To String
     * @param date
     * @return String date
     */
    public static String formatStrDate(LocalDate date) {
        return date.format(df);
    }

    /**
     * Parse LocalDate To String
     * @param dateTime
     * @return String datetime
     */
    public static String formatStrDateTime(LocalDateTime dateTime) {
        return dateTime.format(dtf);
    }

}
