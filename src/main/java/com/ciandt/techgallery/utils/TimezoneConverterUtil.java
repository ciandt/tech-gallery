package com.ciandt.techgallery.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Class to converter timestamp to the user's timezone offset.
 *
 */
public class TimezoneConverterUtil {

  /**
   * Convert a date with the timezoneOffset.
   * 
   * @param date to be converted.
   * @param timezoneOffset.
   * @return Date converted with the timezoneOffset
   */
  public static Date timezoneOffsetConverter(Date date, Integer timezoneOffset) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MINUTE, timezoneOffset);
    return cal.getTime();
  }
}
