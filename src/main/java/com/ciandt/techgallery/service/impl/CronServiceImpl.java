package com.ciandt.techgallery.service.impl;

import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.dao.CronJobDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyCommentDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyFollowersDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyRecommendationDAO;
import com.ciandt.techgallery.persistence.dao.impl.CronJobDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyCommentDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyFollowersDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyRecommendationDAOImpl;
import com.ciandt.techgallery.persistence.model.CronJob;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyFollowers;
import com.ciandt.techgallery.service.CronService;
import com.ciandt.techgallery.service.EmailService;
import com.ciandt.techgallery.service.enums.CronStatus;
import com.ciant.techgallery.transaction.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
public class CronServiceImpl implements CronService {

  /*
   * Attributes --------------------------------------------
   */
  private static CronServiceImpl instance;
  private EmailService emailService = EmailServiceImpl.getInstance();
  private CronJobDAO cronJobsDao = CronJobDAOImpl.getInstance();
  private TechnologyFollowersDAO technologyFollowersDao = TechnologyFollowersDAOImpl.getInstance();
  private TechnologyRecommendationDAO technologyRecommendationDao =
      TechnologyRecommendationDAOImpl.getInstance();
  private TechnologyCommentDAO technologyCommentDao = TechnologyCommentDAOImpl.getInstance();

  /*
   * Constructors --------------------------------------------
   */
  private CronServiceImpl() {}

  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 09/10/2015
   *
   * @return EmailServiceImpl instance.
   */
  public static CronServiceImpl getInstance() {
    if (instance == null) {
      instance = new CronServiceImpl();
    }
    return instance;
  }

  /**
   * Push one email to email queue for each follower user in each technology that have
   * followers.
   */
  public void sendEmailtoFollowers() {
    CronJob cronJob = new CronJob();
    cronJob.setName(Constants.CRON_MAIL_JOB);
    cronJob.setStartTimestamp(new Date());
    try {
      
      List<TechnologyFollowers> techFollowers = technologyFollowersDao.findAll();
      if (techFollowers != null && techFollowers.size() > 0) {
        for (TechnologyFollowers technologyFollowers : techFollowers) {
          Technology technology = technologyFollowers.getTechnology().get();
          if (technologyFollowers.getFollowers().size() > 0) {
            Date lastCronJobExecDate;
            CronJob lastCronJob = cronJobsDao.findLastExecutedCronJob(Constants.CRON_MAIL_JOB);
            if (lastCronJob != null) {
              lastCronJobExecDate = lastCronJob.getStartTimestamp();
            } else {
              Calendar cal = Calendar.getInstance();
              cal.add(Calendar.DAY_OF_MONTH, -1);
              lastCronJobExecDate = cal.getTime();
            }
            String dailyRecommendationsIds =
                technologyRecommendationDao
                    .findAllRecommendationsIdsStartingFrom(lastCronJobExecDate);
            String dailyCommentsIds =
                technologyCommentDao.findAllCommentsIdsStartingFrom(lastCronJobExecDate);
            List<Ref<TechGalleryUser>> followers = technologyFollowers.getFollowers();
            if (!dailyRecommendationsIds.isEmpty() || !dailyCommentsIds.isEmpty()) {
              for (Ref<TechGalleryUser> ref : followers) {
                TechGalleryUser follower = ref.get();
                emailService.push(follower, technology, dailyRecommendationsIds, dailyCommentsIds);
              }
            }
          }
        }
      }
      cronJob.setEndTimestamp(new Date());
      cronJob.setCronStatus(CronStatus.SUCCESS);
    } catch (Exception e) {
      cronJob.setEndTimestamp(new Date());
      cronJob.setCronStatus(CronStatus.FAILURE);
      cronJob.setDescription(e.getMessage());
    }
    cronJobsDao.add(cronJob);
  }
}
