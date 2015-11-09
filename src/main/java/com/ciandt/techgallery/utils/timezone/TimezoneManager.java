package com.ciandt.techgallery.utils.timezone;

import java.util.Calendar;
import java.util.Date;

/**
 * Used to show a Date in user's timezone offset. Dates saved in datastore are in UTC, so we must
 * convert it with the offset of the user saved in user's profile when it log in. This class saves
 * a ThreadLocal variable timezone to be used in converted Dates.
 * 
 */
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
