package com.github.duychuongvn.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeUtil {
    public static final String YEAR_MONTH_DAY_TIME_FULL_PATTERN_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ssZZ";
    public static final String YEAR_MONTH_DAY_TIME_FULL_PATTERN_WITHOUT_SPECIAL_CHARACTERS = "yyyyMMddHHmmss";
    public static final String YEAR_MONTH_DAY_TIME_FULL_PATTERN_WITH_SPECIAL_CHARACTERS = "yyyy-MM-dd HH:mm:ss";
    public static final String MONTH_DAY_YEAR_TIME_FULL_PATTERN_WITHOUT_SPECIAL_CHARACTERS = "MMddyyyyHHmmss";
    public static final String MONTH_DAY_YEAR_TIME_FULL_PATTERN_WITH_SPECIAL_CHARACTERS = "MM-dd-yyyy HH:mm:ss";
    public static final String DAY_MONTH_YEAR_TIME_FULL_PATTERN_WITHOUT_SPECIAL_CHARACTERS = "ddMMyyyyHHmmss";
    public static final String DAY_MONTH_YEAR_TIME_FULL_PATTERN_WITH_SPECIAL_CHARACTERS = "dd-MM-yyyy HH:mm:ss";
    public static final String YEAR_MONTH_DATE = "yyyyMMdd";

    public static String convertTimeZone(String inputDateTime, String inputDateTimeFormat, String fromTimeZone, String toTimeZone,
                                         String outputDateTimeFormat) {

        Calendar fromDate = getInstanceOfCalendarWithTimeZone(inputDateTime, inputDateTimeFormat, fromTimeZone);
        return convertTimeZone(fromDate, toTimeZone, outputDateTimeFormat);
    }

    public static String convertTimeZone(Calendar fromDate, String toTimeZone, String outputDateTimeFormat) {
        return formatDateToTimeZone(fromDate.getTime(), toTimeZone, outputDateTimeFormat);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
    public static String formatDate(LocalDate date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(dateTimeFormatter);
    }
    public static String formatDateToTimeZone(Date fromDate, String toTimeZone, String outputDateTimeFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(outputDateTimeFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone(toTimeZone));
        return dateFormat.format(fromDate.getTime());
    }

    public static Calendar getInstanceOfCalendarWithTimeZone(String dateInputValue, String dateInputFormat, String timeZone) {
        Calendar calendarInstance = Calendar.getInstance();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateInputFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            calendarInstance.setTime(dateFormat.parse(dateInputValue));
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                String.format("Input value is invalid: inputDateTime=%s, inputDateTimeFormat=%s, timeZone=%s", dateInputValue, dateInputFormat, timeZone));
        }
        return calendarInstance;
    }

    public static Calendar getInstanceOfCalendar(String dateInputValue, String dateInputFormat) {
        Calendar calendarInstance = Calendar.getInstance();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateInputFormat);
            calendarInstance.setTime(dateFormat.parse(dateInputValue));
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                String.format("Input value is invalid: inputDateTime=%s, inputDateTimeFormat=%s", dateInputValue, dateInputFormat));
        }
        return calendarInstance;
    }

    public static String getTimezone(String clientTimestamp) {
        String timezone = "";
        String patternStr = "((\\+|\\-)(\\d|:){4,5})";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(clientTimestamp);
        if (matcher.find()) {
            timezone = matcher.group();
        }
        return timezone;
    }

    public static int getDiffInDay(Date newDate, Date oldDate) {
        return Math.round((float) ((newDate.getTime() - oldDate.getTime()) / 86400000L));
    }

    public static int getDiffInMinute(Date newDate, Date oldDate) {
        return Math.round((float) ((newDate.getTime() - oldDate.getTime()) / 60000L));
    }

    public static String convertDateToString(Date date, String format) {
        if (date != null && !date.toString().equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            String dateToString = dateFormat.format(date);
            return dateToString;
        } else {
            return "";
        }
    }

    public static boolean isValidDateRange(Date startDate, Date endDate, boolean dateIsEqual) {
        if (startDate == null || endDate == null) {
            return false;
        }

        if (dateIsEqual) {
            if (startDate.equals(endDate)) {
                return true;
            }
        }

        if (endDate.after(startDate)) {
            return true;
        }

        return false;
    }

    public static String getGmtServerTimezone() {
        int gmtOffset = Calendar.getInstance().getTimeZone().getRawOffset();
        long hours = TimeUnit.MILLISECONDS.toHours(gmtOffset);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(gmtOffset) - TimeUnit.HOURS.toMinutes(hours);
        if (hours > 0) {
            return String.format("GMT+%d%02d", hours, minutes);
        } else {

            return String.format("GMT%d%02d", hours, minutes);
        }
    }

}
