package com.ciandt.techgallery.persistence.dao;

import com.ciandt.techgallery.persistence.model.CronJob;

public interface CronJobDAO extends GenericDAO<CronJob, Long> {

  /**
   * Find last executed cron job by its name.
   * 
   * @param name of the cron job. Ex.: /cron/mail .
   * @return cronjob.
   */
  public CronJob findLastExecutedCronJob(String name);
  
}
