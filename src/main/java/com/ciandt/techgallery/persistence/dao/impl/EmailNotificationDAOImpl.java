package com.ciandt.techgallery.persistence.dao.impl;

import com.ciandt.techgallery.persistence.dao.EmailNotificationDAO;
import com.ciandt.techgallery.persistence.model.EmailNotification;


public class EmailNotificationDAOImpl extends GenericDAOImpl<EmailNotification, Long> implements
    EmailNotificationDAO {

  /*
   * Attributes --------------------------------------------
   */
  private static EmailNotificationDAOImpl instance;

  /*
   * Constructor --------------------------------------------
   */
  private EmailNotificationDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 08/10/2015
   *
   * @return EmailNotificationDAOImpl instance.
   */
  public static EmailNotificationDAOImpl getInstance() {
    if (instance == null) {
      instance = new EmailNotificationDAOImpl();
    }
    return instance;
  }
  
}
