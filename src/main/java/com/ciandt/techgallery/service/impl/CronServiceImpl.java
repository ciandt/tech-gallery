package com.ciandt.techgallery.service.impl;

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
import com.ciandt.techgallery.service.model.email.TechGalleryActivitiesEmailTemplateTO;
import com.ciandt.techgallery.service.model.email.TechnologyActivitiesEmailTemplateTO;
import com.ciandt.techgallery.servlets.CronActivityResumeServlet;
import com.ciandt.techgallery.utils.timezone.TimezoneManager;
import com.ciant.techgallery.transaction.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Transactional
public class CronServiceImpl implements CronService {

  /*
   * Attributes --------------------------------------------
   */
  public static final Logger _LOG = Logger.getLogger(CronActivityResumeServlet.class.getName());
  private static CronServiceImpl instance;
  private EmailService emailService = EmailServiceImpl.getInstance();
  private TechnologyActivitiesService technologyActivitiesService =
      TechnologyActivitiesServiceImpl.getInstance();
  private CronJobDAO cronJobsDao = CronJobDAOImpl.getInstance();
  private TechnologyRecommendationDAO technologyRecommendationDao =
      TechnologyRecommendationDAOImpl.getInstance();
  private TechnologyCommentDAO technologyCommentDao = TechnologyCommentDAOImpl.getInstance();
  private TechGalleryUserDAO techGalleryUserDao = TechGalleryUserDAOImpl.getInstance();
  private TechnologyDAO technologyDao = TechnologyDAOImpl.getInstance();
  private EndorsementDAO endorsementDao = EndorsementDAOImpl.getInstance();

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
   * Push one email to email queue for each follower user in each technology that have followers.
   */
  public void sendEmailtoFollowers() {
    CronJob cronJob = new CronJob();
    cronJob.setName(Constants.CRON_MAIL_ACTIVITY_JOB);
    cronJob.setStartTimestamp(new Date());

    try {
      List<TechGalleryUser> followers = techGalleryUserDao.findAllFollowers();
      if (followers != null && followers.size() > 0) {
        for (TechGalleryUser follower : followers) {
          // Set user's timezone offset. To be used in converted dates.
          TimezoneManager.getInstance().setOffset(follower.getTimezoneOffset());

          // TO used in mustache template
          TechGalleryActivitiesEmailTemplateTO techGalleryActivitiesTo =
              new TechGalleryActivitiesEmailTemplateTO();
          techGalleryActivitiesTo.setTimestamp(new Date());
          techGalleryActivitiesTo.setFollower(follower);
          techGalleryActivitiesTo.setAppName(Constants.APP_NAME);
          List<TechnologyActivitiesEmailTemplateTO> techActivitiesToList =
              new ArrayList<TechnologyActivitiesEmailTemplateTO>();

          for (String id : follower.getFollowedTechnologyIds()) {
            Technology technology = technologyDao.findById(id);
            Date lastCronJobExecDate = findLastExecutedCronJob(Constants.CRON_MAIL_ACTIVITY_JOB);
            findNewActivitiesInATechnology(techActivitiesToList, follower, technology,
                lastCronJobExecDate);
          }
          techGalleryActivitiesTo.setTechnologyActivitiesTo(techActivitiesToList);

          // Push email to queue if has new activities
          if (!techActivitiesToList.isEmpty()) {
            EmailConfig email = new EmailConfig(EmailTypeEnum.DAILY_RESUME,
                EmailTypeEnum.DAILY_RESUME.getSubject()
                    + techGalleryActivitiesTo.getFormattedTimestamp(),
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

  private void findNewActivitiesInATechnology(
      List<TechnologyActivitiesEmailTemplateTO> techActivitiesToList, TechGalleryUser follower,
      Technology technology, Date lastCronJobExecDate) {
    // Find new Activities in a technology
    List<TechnologyRecommendation> dailyRecommendations = technologyRecommendationDao
        .findAllRecommendationsStartingFrom(technology, lastCronJobExecDate);
    dailyRecommendations = removeUserRecommendations(follower, dailyRecommendations);

    List<TechnologyComment> dailyComments =
        technologyCommentDao.findAllCommentsStartingFrom(technology, lastCronJobExecDate);
    dailyComments = removeUserComments(follower, dailyComments);

    // Remove Recommendations' comments. For avoid duplication
    if (!dailyRecommendations.isEmpty()) {
      for (TechnologyRecommendation recommendation : dailyRecommendations) {
        if (!dailyComments.isEmpty()) {
          dailyComments.remove(recommendation.getComment().get());
        }
      }
    }

    if (!dailyRecommendations.isEmpty() || !dailyComments.isEmpty()) {
      // Create a TO to each technology
      TechnologyActivitiesEmailTemplateTO techActivitiesTo = technologyActivitiesService
          .createTechnologyActivitiesTo(technology, dailyRecommendations, dailyComments);
      techActivitiesToList.add(techActivitiesTo);
    }
  }

  private List<TechnologyComment> removeUserComments(TechGalleryUser follower,
      List<TechnologyComment> dailyComments) {
    List<TechnologyComment> dailyCommentsWithoutUser = new ArrayList<TechnologyComment>();
    if (dailyComments != null) {
      for (TechnologyComment technologyComment : dailyComments) {
        if (!technologyComment.getAuthor().get().equals(follower)) {
          dailyCommentsWithoutUser.add(technologyComment);
        }
      }
    }
    return dailyCommentsWithoutUser;
  }

  private List<TechnologyRecommendation> removeUserRecommendations(TechGalleryUser follower,
      List<TechnologyRecommendation> dailyRecommendations) {
    List<TechnologyRecommendation> dailyRecommendationsWithoutUser =
        new ArrayList<TechnologyRecommendation>();
    if (dailyRecommendations != null) {
      for (TechnologyRecommendation technologyRecommendation : dailyRecommendations) {
        if (!technologyRecommendation.getRecommender().get().equals(follower)) {
          dailyRecommendationsWithoutUser.add(technologyRecommendation);
        }
      }
    }
    return dailyRecommendationsWithoutUser;
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
    Date lastExecutedCronJob = findLastExecutedCronJob(Constants.CRON_MAIL_ENDORSEMENT_JOB);
    try {
      List<TechGalleryUser> usersList = techGalleryUserDao.findAll();
      for (TechGalleryUser techGalleryUser : usersList) {
        List<Endorsement> endorsementsList =
            endorsementDao.findAllEndorsementsStartingFrom(techGalleryUser, lastExecutedCronJob);
        if (endorsementsList != null) {
          TechGalleryActivitiesEmailTemplateTO activities =
              new TechGalleryActivitiesEmailTemplateTO(Constants.APP_NAME, null,
                  new ArrayList<TechnologyActivitiesEmailTemplateTO>());

          // group and define user's activities
          setUserActivities(endorsementsList, activities);

          // Push email to queue if has new activities
          if (!activities.getTechnologyActivitiesTo().isEmpty()) {
            EmailConfig email = new EmailConfig(EmailTypeEnum.ENDORSED,
                EmailTypeEnum.ENDORSED.getSubject() + activities.getFormattedTimestamp(),
                activities, techGalleryUser.getEmail());
            emailService.push(email);
          }
        }
      }
      cronJob.setEndTimestamp(new Date());
      cronJob.setCronStatus(CronStatus.SUCCESS);
    } catch (Exception e) {
      _LOG.log(Level.SEVERE, "Cron exception: ", e);
      cronJob.setEndTimestamp(new Date());
      cronJob.setCronStatus(CronStatus.FAILURE);
      cronJob.setDescription(e.getMessage());
    }
    cronJobsDao.add(cronJob);
  }

  /**
   * Method that groups endorsements by technologies.
   * 
   * @param endorsementsList list of endorsements.
   * @return map with grouped endorsements.
   */
  private Map<String, List<Endorsement>> groupEndorsementsByTechnology(
      List<Endorsement> endorsementsList) {
    Map<String, List<Endorsement>> groupedEndorsements = new HashMap<String, List<Endorsement>>();
    for (Endorsement endorsement : endorsementsList) {
      String key = endorsement.getTechnologyEntity().getName();

      List<Endorsement> group = groupedEndorsements.get(key);
      if (group == null) {
        group = new ArrayList<Endorsement>();
        groupedEndorsements.put(key, group);
      }
      group.add(endorsement);
    }
    return groupedEndorsements;
  }

  /**
   * Method that set the user activities by grouping endorsements by technology.
   * 
   * @param endorsementsList list of user endorsements.
   * @param activities activities for the email.
   */
  private void setUserActivities(List<Endorsement> endorsementsList,
      TechGalleryActivitiesEmailTemplateTO activities) {
    Map<String, List<Endorsement>> groupedEndorsements =
        groupEndorsementsByTechnology(endorsementsList);
    _LOG.info("Technology hashmap size: " + groupedEndorsements.size());

    for (Map.Entry<String, List<Endorsement>> entry : groupedEndorsements.entrySet()) {
      List<Endorsement> technologyEndorsements = entry.getValue();
      if (technologyEndorsements != null && technologyEndorsements.size() > 0) {
        TechnologyActivitiesEmailTemplateTO endorsementActivity;

        Endorsement endorsement = technologyEndorsements.get(0);
        endorsementActivity = new TechnologyActivitiesEmailTemplateTO(
            endorsement.getEndorserEntity().getName(), endorsement.getTechnologyEntity(),
            Constants.EMAIL_CONTEXT_SINGLE, null, null, null);

        if (technologyEndorsements.size() > 1) {
          endorsementActivity.setContext(Constants.EMAIL_CONTEXT_PLURAL);
          _LOG.info("Endorsement for Technology: " + entry.getKey() + " qty: "
              + technologyEndorsements.size());

          for (int i = 1; i < technologyEndorsements.size(); i++) {
            endorsement = technologyEndorsements.get(i);
            if (i == technologyEndorsements.size() - 1) {
              endorsementActivity.addEndorser(endorsement.getEndorserEntity().getName(), true);
            } else {
              endorsementActivity.addEndorser(endorsement.getEndorserEntity().getName(), false);
            }
          }
        }
        activities.getTechnologyActivitiesTo().add(endorsementActivity);
      }
    }
  }

}
