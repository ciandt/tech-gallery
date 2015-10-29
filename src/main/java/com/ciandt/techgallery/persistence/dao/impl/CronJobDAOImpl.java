package com.ciandt.techgallery.persistence.dao.impl;

import com.googlecode.objectify.Objectify;

import com.ciandt.techgallery.ofy.OfyService;
import com.ciandt.techgallery.persistence.dao.CronJobDAO;
import com.ciandt.techgallery.persistence.model.CronJob;
import com.ciandt.techgallery.service.enums.CronStatus;

public class CronJobDAOImpl extends GenericDAOImpl<CronJob, Long> implements CronJobDAO {

  /*
   * Attributes --------------------------------------------
   */
  private static CronJobDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private CronJobDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 08/10/2015
   *
   * @return CronJobsDAOImpl instance.
   */
  public static CronJobDAOImpl getInstance() {
    if (instance == null) {
      instance = new CronJobDAOImpl();
    }
    return instance;
  }

  @Override
  public CronJob findLastExecutedCronJob(String name) {
    Objectify objectify = OfyService.ofy();
    CronJob job =
        objectify.load().type(CronJob.class).order("-" + CronJob.START_TIMESTAMP)
            .filter(CronJob.NAME, name).filter(CronJob.CRON_STATUS, CronStatus.SUCCESS).first()
            .now();
    return job;
  }
}
