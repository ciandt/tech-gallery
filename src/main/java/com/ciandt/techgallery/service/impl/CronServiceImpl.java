package com.ciandt.techgallery.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.dao.CronJobDAO;
import com.ciandt.techgallery.persistence.dao.EndorsementDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyCommentDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyRecommendationDAO;
import com.ciandt.techgallery.persistence.dao.impl.CronJobDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.EndorsementDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyCommentDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyRecommendationDAOImpl;
import com.ciandt.techgallery.persistence.model.CronJob;
import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.CronService;
import com.ciandt.techgallery.service.EmailService;
import com.ciandt.techgallery.service.TechnologyActivitiesService;
import com.ciandt.techgallery.service.email.EmailConfig;
import com.ciandt.techgallery.service.enums.CronStatus;
import com.ciandt.techgallery.service.enums.EmailTypeEnum;
import com.ciandt.techgallery.service.model.TechGalleryActivitiesTO;
import com.ciandt.techgallery.service.model.TechnologyActivitiesTO;
import com.ciandt.techgallery.servlets.CronActivityResumeServlet;
import com.ciant.techgallery.transaction.Transactional;

@Transactional
public class CronServiceImpl implements CronService {

  /*
   * Attributes --------------------------------------------
   */
  public static final Logger _LOG = Logger.getLogger(CronActivityResumeServlet.class.getName());
  private static CronServiceImpl instance;
  private EmailService emailService = EmailServiceImpl.getInstance();
  private TechnologyActivitiesService technologyActivitiesService = TechnologyActivitiesServiceImpl.getInstance();
  private CronJobDAO cronJobsDao = CronJobDAOImpl.getInstance();
  private TechnologyRecommendationDAO technologyRecommendationDao = TechnologyRecommendationDAOImpl.getInstance();
  private TechnologyCommentDAO technologyCommentDao = TechnologyCommentDAOImpl.getInstance();
  private TechGalleryUserDAO techGalleryUserDao = TechGalleryUserDAOImpl.getInstance();
  private TechnologyDAO technologyDao = TechnologyDAOImpl.getInstance();
  private EndorsementDAO endorsementDao = EndorsementDAOImpl.getInstance();

  /*
   * Constructors --------------------------------------------
   */
  private CronServiceImpl() {
  }

  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros
   *         Moreira </a>
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
   * Push one email to email queue for each follower user in each technology
   * that have followers.
   */
  public void sendEmailtoFollowers() {
    CronJob cronJob = new CronJob();
    cronJob.setName(Constants.CRON_MAIL_ACTIVITY_JOB);
    cronJob.setStartTimestamp(new Date());

    try {
      List<TechGalleryUser> followers = techGalleryUserDao.findAllFollowers();
      if (followers != null && followers.size() > 0) {
        for (TechGalleryUser follower : followers) {
          // TO used in mustache template
          TechGalleryActivitiesTO techGalleryActivitiesTo = new TechGalleryActivitiesTO();
          techGalleryActivitiesTo.setTimestamp(new Date());
          techGalleryActivitiesTo.setFollower(follower);
          techGalleryActivitiesTo.setAppName(Constants.APP_NAME);
          List<TechnologyActivitiesTO> techActivitiesToList = new ArrayList<TechnologyActivitiesTO>();

          for (String id : follower.getFollowedTechnologyIds()) {
            Technology technology = technologyDao.findById(id);
            Date lastCronJobExecDate = findLastExecutedCronJob(Constants.CRON_MAIL_ACTIVITY_JOB);
            findNewActivitiesInATechnology(techActivitiesToList, technology, lastCronJobExecDate);
          }
          techGalleryActivitiesTo.setTechnologyActivitiesTo(techActivitiesToList);

          // Push email to queue if has new activities
          if (!techActivitiesToList.isEmpty()) {
            EmailConfig email = new EmailConfig(EmailTypeEnum.DAILY_RESUME,
                EmailTypeEnum.DAILY_RESUME.getSubject() + techGalleryActivitiesTo.getFormattedTimestamp(),
                techGalleryActivitiesTo, follower.getEmail());
            emailService.push(email);
          }
        }
      }
      cronJob.setEndTimestamp(new Date());
      cronJob.setCronStatus(CronStatus.SUCCESS);
    } catch (Exception e) {
      _LOG.info(e.getMessage());
      cronJob.setEndTimestamp(new Date());
      cronJob.setCronStatus(CronStatus.FAILURE);
      cronJob.setDescription(e.getMessage());
    }
    cronJobsDao.add(cronJob);
  }

  private void findNewActivitiesInATechnology(List<TechnologyActivitiesTO> techActivitiesToList, Technology technology,
      Date lastCronJobExecDate) {
    // Find new Activities in a technology
    List<TechnologyRecommendation> dailyRecommendations = technologyRecommendationDao
        .findAllRecommendationsStartingFrom(technology, lastCronJobExecDate);
    List<TechnologyComment> dailyComments = technologyCommentDao.findAllCommentsStartingFrom(technology,
        lastCronJobExecDate);

    // Remove Recommendations' comments. For avoid duplication
    if (dailyRecommendations != null) {
      for (TechnologyRecommendation recommendation : dailyRecommendations) {
        if (dailyComments != null) {
          dailyComments.remove(recommendation.getComment().get());
        }
      }
    }

    if (dailyRecommendations != null || dailyComments != null) {
      // Create a TO to each technology
      TechnologyActivitiesTO techActivitiesTo = technologyActivitiesService.createTechnologyActivitiesTo(technology,
          dailyRecommendations, dailyComments);
      techActivitiesToList.add(techActivitiesTo);
    }
  }

  private Date findLastExecutedCronJob(String cronJob) {
    Date lastCronJobExecDate;
    CronJob lastCronJob = cronJobsDao.findLastExecutedCronJob(cronJob);
    if (lastCronJob != null) {
      lastCronJobExecDate = lastCronJob.getStartTimestamp();
    } else {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_MONTH, -1);
      lastCronJobExecDate = cal.getTime();
    }
    return lastCronJobExecDate;
  }

  @Override
  public void sendEmailToEndorseds() {
    CronJob cronJob = new CronJob();
    cronJob.setName(Constants.CRON_MAIL_ENDORSEMENT_JOB);
    cronJob.setStartTimestamp(new Date());

    try {
      List<TechGalleryUser> usersList = techGalleryUserDao.findAll();
      for (TechGalleryUser techGalleryUser : usersList) {
        List<Endorsement> endorsementsList = endorsementDao.findAllEndorsementsStartingFrom(techGalleryUser,
            findLastExecutedCronJob(Constants.CRON_MAIL_ENDORSEMENT_JOB));
        if (endorsementsList != null) {
          TechGalleryActivitiesTO activities = new TechGalleryActivitiesTO();
          activities.setTechnologyActivitiesTo(new ArrayList<TechnologyActivitiesTO>());
          activities.setAppName(Constants.APP_NAME);
          activities.setTimestamp(new Date());
          for (Endorsement endorsement : endorsementsList) {
            TechnologyActivitiesTO endorsementActivity = new TechnologyActivitiesTO();
            endorsementActivity.setEndorserUser(endorsement.getEndorserEntity());
            endorsementActivity.setTechnology(endorsement.getTechnologyEntity());
            activities.getTechnologyActivitiesTo().add(endorsementActivity);
          }
          // Push email to queue if has new activities
          if (!activities.getTechnologyActivitiesTo().isEmpty()) {
            EmailConfig email = new EmailConfig(EmailTypeEnum.ENDORSED,
                EmailTypeEnum.ENDORSED.getSubject() + activities.getFormattedTimestamp(), activities,
                techGalleryUser.getEmail());
            emailService.push(email);
          }
        }
      }
      cronJob.setEndTimestamp(new Date());
      cronJob.setCronStatus(CronStatus.SUCCESS);
    } catch (Exception e) {
      _LOG.info(e.getMessage());
      cronJob.setEndTimestamp(new Date());
      cronJob.setCronStatus(CronStatus.FAILURE);
      cronJob.setDescription(e.getMessage());
    }
    cronJobsDao.add(cronJob);
  }

}
