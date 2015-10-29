package com.ciandt.techgallery.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.dao.EmailNotificationDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyCommentDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyRecommendationDAO;
import com.ciandt.techgallery.persistence.dao.impl.EmailNotificationDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyCommentDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyRecommendationDAOImpl;
import com.ciandt.techgallery.persistence.model.EmailNotification;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.EmailService;
import com.ciandt.techgallery.service.email.EmailConfig;
import com.ciandt.techgallery.service.enums.EmailTypeEnum;
import com.ciandt.techgallery.service.model.TechnologyActivitiesTO;
import com.ciant.techgallery.transaction.Transactional;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.apphosting.api.ApiProxy;
import com.googlecode.objectify.Key;

@Transactional
public class EmailServiceImpl implements EmailService {

  private static final String PRODUCTION_PROPERTY = "Production";
  private static final String APPLICATION_VERSION_PROPERTY = "com.google.appengine.application.version";
  private static final String APPLICATION_ID_PROPERTY = "com.google.appengine.application.id";
  private static final String RUNTIME_ENVIRONMENT_PROPERTY = "com.google.appengine.runtime.environment";
  private static final String LINK_LOCALHOST = "http://localhost:8888/";
  private static final String PATH_VIEW_TECH_HTML = "viewTech.html";

  private InternetAddress from = null;
  private EmailNotificationDAO emailNotificationDao = EmailNotificationDAOImpl.getInstance();
  private TechnologyRecommendationDAO technologyRecommendationDao = TechnologyRecommendationDAOImpl.getInstance();
  private TechnologyCommentDAO technologyCommentDao = TechnologyCommentDAOImpl.getInstance();
  private TechGalleryUserDAOImpl techGalleryUserDao = TechGalleryUserDAOImpl.getInstance();
  private TechnologyDAOImpl technologyDao = TechnologyDAOImpl.getInstance();

  /*
   * Attributes --------------------------------------------
   */
  private static EmailServiceImpl instance;

  private static final Logger log = Logger.getLogger(EmailServiceImpl.class.getName());
  private static final String queueName = "email-queue";
  private static final String queueUrl = "/mail";

  /*
   * Constructors --------------------------------------------
   */
  private EmailServiceImpl() {
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
  public static EmailServiceImpl getInstance() {
    if (instance == null) {
      instance = new EmailServiceImpl();
    }
    return instance;
  }

  /**
   * Push email to queue.
   */
  @Override
  public void push(TechGalleryUser user, Technology technology, String recommendationsIds, String commentsIds) {
    QueueFactory.getQueue(queueName)
        .add(TaskOptions.Builder.withUrl(queueUrl).param("userId", user.getId().toString())
            .param("technologyId", technology.getId()).param("recommendationsIds", recommendationsIds)
            .param("commentsIds", commentsIds));
  }

  @Override
  public void push(TechGalleryUser endorserUser, TechGalleryUser endorsedUser, Technology technology) {
    QueueFactory.getQueue(queueName)
        .add(TaskOptions.Builder.withUrl(queueUrl).param("userId", endorserUser.getId().toString())
            .param("endorsedUser", endorsedUser.getId().toString()).param("technologyId", technology.getId()));
  }

  /**
   * Execute email from queue.
   */
  @Override
  public void execute(String userId, String technologyId, String recommendationsIds, String commentsIds,
      String serverUrl) {
    List<TechnologyComment> comments = new ArrayList<TechnologyComment>();
    if (!commentsIds.isEmpty()) {
      String[] commentIds = commentsIds.split(",");
      for (String id : commentIds) {
        if (!id.isEmpty()) {
          comments.add(technologyCommentDao.findById(Long.parseLong(id)));
        }
      }
    }
    List<TechnologyRecommendation> recommendations = new ArrayList<TechnologyRecommendation>();
    if (!recommendationsIds.isEmpty()) {
      String[] recommendIds = recommendationsIds.split(",");
      for (String id : recommendIds) {
        if (!id.isEmpty()) {
          TechnologyRecommendation recommedation = technologyRecommendationDao.findById(Long.parseLong(id));
          recommendations.add(recommedation);
          comments.remove(recommedation.getCommentEntity());
        }
      }
    }
    TechGalleryUser user = techGalleryUserDao.findById(Long.parseLong(userId));
    Technology technology = technologyDao.findById(technologyId);

    TechnologyActivitiesTO techActivities = createEmailTO(user, null, technology, comments, recommendations, new Date(),
        Constants.APP_NAME);

    EmailConfig email = new EmailConfig(
        EmailTypeEnum.DAILY_RESUME_MAIL.getSubject() + technology.getName() + " - "
            + techActivities.getFormattedTimestamp(),
        "emailtemplates" + File.separator + EmailTypeEnum.DAILY_RESUME_MAIL.getTemplate(), techActivities, null,
        EmailTypeEnum.DAILY_RESUME_MAIL.getReason(), user.getEmail());
    sendEmail(email);
  }

  private TechnologyActivitiesTO createEmailTO(TechGalleryUser user, TechGalleryUser endorserUser,
      Technology technology, List<TechnologyComment> comments, List<TechnologyRecommendation> recommendations,
      Date timestamp, String appName) {
    TechnologyActivitiesTO techActivities = new TechnologyActivitiesTO();
    techActivities.setUser(user);
    techActivities.setComments(comments);
    techActivities.setRecommendations(recommendations);
    techActivities.setTechnology(technology);
    techActivities.setTimestamp(new Date());
    techActivities.setAppName(appName);
    techActivities.setEndorserUser(endorserUser);
    techActivities.setTechnologyLink(generateTechnologyLink(technology));
    return techActivities;
  }

  private String generateTechnologyLink(Technology tech) {
    String linkTechnology;
    String queryString = "?id=" + tech.getId();
    String environment = System.getProperty(RUNTIME_ENVIRONMENT_PROPERTY);
    if (StringUtils.equals(PRODUCTION_PROPERTY, environment)) {
      String applicationId = System.getProperty(APPLICATION_ID_PROPERTY);
      String version = System.getProperty(APPLICATION_VERSION_PROPERTY);
      String versionName = version.split("\\.")[0];
      linkTechnology = "https://" + versionName + "-dot-" + applicationId + ".appspot.com/";
    } else {
      linkTechnology = LINK_LOCALHOST;
    }
    return linkTechnology + PATH_VIEW_TECH_HTML + queryString;
  }

  @Override
  public void execute(String userId, String endorsedUser, String technologyId) {
    TechGalleryUser endorser = techGalleryUserDao.findById(Long.parseLong(userId));
    TechGalleryUser endorsed = techGalleryUserDao.findById(Long.parseLong(endorsedUser));
    Technology technology = technologyDao.findById(technologyId);

    TechnologyActivitiesTO techActivities = createEmailTO(null, endorser, technology, null, null, new Date(),
        Constants.APP_NAME);

    EmailConfig email = new EmailConfig(EmailTypeEnum.ENDORSED_MAIL.getSubject() + technology.getName(),
        "emailtemplates" + File.separator + EmailTypeEnum.ENDORSED_MAIL.getTemplate(), techActivities, null,
        EmailTypeEnum.ENDORSED_MAIL.getReason(), endorsed.getEmail());
    sendEmail(email);
  }

  private void sendEmail(EmailConfig email) {
    try {
      Message msg = prepareMessage(email);
      Transport.send(msg);
      registerEmailNotification(email, true);

    } catch (Throwable err) {
      Long notificationId = registerEmailNotification(email, false);
      log.log(Level.SEVERE, "Error when attempting to send email " + notificationId, err);
    }
  }

  private Message prepareMessage(EmailConfig email) throws UnsupportedEncodingException, MessagingException {

    email.processTemplate();
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    MimeMessage msg = new MimeMessage(session);
    msg.setFrom(getFrom());
    for (String to : email.getTo()) {
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    }
    msg.setContent(email.getBody(), "text/html");
    msg.setSubject(email.getSubject(), "UTF-8");
    return msg;
  }

  private long registerEmailNotification(EmailConfig email, boolean success) {
    EmailNotification emailNotification = new EmailNotification();
    emailNotification.setRecipients(Arrays.asList(email.getTo()));
    emailNotification.setRule(email.getRule());
    emailNotification.setReason(email.getReason());
    emailNotification.setTimestampSend(System.currentTimeMillis());
    emailNotification.setEmailStatus(success ? "SUCCESS" : "FAILURE");

    Key<EmailNotification> key = emailNotificationDao.add(emailNotification);
    return key.getId();
  }

  private InternetAddress getFrom() throws UnsupportedEncodingException {
    if (from == null) {
      String appId = ApiProxy.getCurrentEnvironment().getAppId();
      int tilde = appId.indexOf('~');
      if (tilde >= 0) { // TODO make this into an environment property
        appId = appId.substring(tilde + 1);
      }
      String addr = "no-reply@" + appId + ".com";
      log.info("app email from address set to: " + Constants.APP_EMAIL);
      from = new InternetAddress(Constants.APP_EMAIL, addr);
    }
    return from;
  }
}
