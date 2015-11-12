package com.ciandt.techgallery.service.impl;

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import com.googlecode.objectify.Key;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.dao.EmailNotificationDAO;
import com.ciandt.techgallery.persistence.dao.impl.EmailNotificationDAOImpl;
import com.ciandt.techgallery.persistence.model.EmailNotification;
import com.ciandt.techgallery.service.EmailService;
import com.ciandt.techgallery.service.email.EmailConfig;
import com.ciandt.techgallery.utils.TechGalleryUtil;
import com.ciant.techgallery.transaction.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
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

  private InternetAddress from = null;
  private EmailNotificationDAO emailNotificationDao = EmailNotificationDAOImpl.getInstance();

  /**
   * Push email to queue.
   */
  public void push(EmailConfig email) {
    QueueFactory.getQueue(queueName).add(
        TaskOptions.Builder.withUrl(queueUrl).param("subject", email.getSubject())
            .param("body", email.getBody()).param("reason", email.getReason())
            .param("to", email.getTo()[0]));
  }

  public void execute(String subject, String body, String reason, String to) {
    sendEmail(new EmailConfig(subject, body, reason, to));
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

  private Message prepareMessage(EmailConfig email) throws UnsupportedEncodingException,
      MessagingException {

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
    emailNotification.setReason(email.getReason());
    emailNotification.setTimestampSend(new Date());
    emailNotification.setEmailStatus(success ? "SUCCESS" : "FAILURE");

    Key<EmailNotification> key = emailNotificationDao.add(emailNotification);
    return key.getId();
  }

  private InternetAddress getFrom() throws UnsupportedEncodingException {
    if (from == null) {
      String addr = "no-reply@" + TechGalleryUtil.getAppId() + ".com";
      log.info("app email from address set to: " + Constants.APP_EMAIL);
      from = new InternetAddress(Constants.APP_EMAIL, addr);
    }
    return from;
  }
}
