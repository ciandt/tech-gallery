package com.ciandt.techgallery.service.impl;

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.apphosting.api.ApiProxy;

import com.googlecode.objectify.Key;

import com.ciandt.techgallery.persistence.dao.EmailNotificationDAO;
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
import com.ciant.techgallery.transaction.Transactional;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Transactional
public class EmailServiceImpl implements EmailService {

  private static final Logger log = Logger.getLogger(EmailServiceImpl.class.getName());
  private static final String queueName = "email-queue";
  private static final String queueUrl = "/mail";
  private static final String subject = "[Tech Gallery] Resumo do dia";
  private static final String reason = "Resumo do dia para os followers";
  private static final String template = "template.example.email";
  
  private InternetAddress from = null;
  private EmailNotificationDAO emailNotificationDao = new EmailNotificationDAOImpl();
  
  @Override
  public void push(TechGalleryUser user, Technology technology,
      List<TechnologyRecommendation> recommendations, List<TechnologyComment> comments) {
    String recommendationsIds = "";
    String commentsIds = "";
    for (TechnologyRecommendation recommendation : recommendations) {
      recommendationsIds.concat("," + recommendation.getId());
    }
    for (TechnologyComment comment : comments) {
      commentsIds.concat("," + comment.getId());
    }
    QueueFactory.getQueue(queueName).add(TaskOptions.Builder.withUrl(queueUrl)
        .param("userId", user.getId().toString())
        .param("technologyId", technology.getId())
        .param("recommendationsIds", recommendationsIds != null ? recommendationsIds : "")
        .param("commentsIds", commentsIds != null ? commentsIds : ""));
  }

  @Override
  public void execute(String userId, String technologyId, String recommendationsIds,
      String commentsIds, String serverUrl) {
    String[] recommendIds = recommendationsIds.split(",");
    String[] commentIds = commentsIds.split(",");
    List<TechnologyRecommendation> recommendations = new ArrayList<TechnologyRecommendation>();
    List<TechnologyComment> comments = new ArrayList<TechnologyComment>();
    for (String id : commentIds) {
      comments.add(TechnologyCommentDAOImpl.getInstance().findById(Long.parseLong(id)));
    }
    for (String id : recommendIds) {
      recommendations.add(TechnologyRecommendationDAOImpl.getInstance()
          .findById(Long.parseLong(id)));
    }
    TechGalleryUser user = TechGalleryUserDAOImpl.getInstance().findById(Long.parseLong(userId));
    Technology technology = TechnologyDAOImpl.getInstance().findById(userId);
    
    //TODO extract method to build template (mustache)
    Map<String, String> variableValue = new HashMap<String, String>();
    variableValue.put("${receiverName}", user.getName());
    variableValue.put("${user}", user.getName());
    variableValue.put("${tecnology}", technology.getName());
    EmailConfig email = new EmailConfig(
        subject, "emailtemplates" + File.separator + template,
        variableValue, null, reason,
        user.getEmail());
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

  private Message prepareMessage(EmailConfig email)
      throws UnsupportedEncodingException, MessagingException {
    
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    Message msg = new MimeMessage(session);
    msg.setFrom(getFrom());
    for (String to : email.getTo()) {
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    }
    msg.setSubject(email.getSubject());
    email.processTemplate();
    msg.setText(email.getBody());
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
    if (from == null ) {
      String appId = ApiProxy.getCurrentEnvironment().getAppId();
      int tilde = appId.indexOf('~');
      if (tilde >= 0) { //TODO make this into an environment property
        appId = appId.substring(tilde + 1);
      }
      String addr = "no-reply@" + appId + ".appspotmail.com";
      log.info("app email from address set to: " + addr);
      from = new InternetAddress(addr, "no-reply@google.com");
    }
    return from;
  }
}
