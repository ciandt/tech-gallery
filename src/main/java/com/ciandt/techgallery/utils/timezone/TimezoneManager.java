package com.ciandt.techgallery.utils.timezone;

import java.util.Calendar;
import java.util.Date;

public class TimezoneManager {

  private static final ThreadLocal<Integer> timezone = new ThreadLocal<Integer>();

  private static final TimezoneManager INSTANCE = new TimezoneManager();

  private TimezoneManager() {}

  public static TimezoneManager getInstance() {
    return INSTANCE;
  }

  public void setOffset(Integer offset) {
    timezone.set(offset);
  }

  /**
   * Convert a date with the timezoneOffset.
   * 
   * @param date to be converted.
   * @return Date converted with the timezoneOffset if offset not null.
   */
  public Date convertToUserTimezone(Date date) {
    Integer offset = timezone.get();
    if (offset != null) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(Calendar.MINUTE, offset);
      return cal.getTime();
    } else {
      return date;
    }
  }
}
