package com.ciandt.techgallery.ofy;

import com.ciandt.techgallery.persistence.model.*;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;


import com.ciandt.techgallery.persistence.model.profile.UserProfile;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Service class for Objectify settings.
 *
 * @author felipers
 *
 */
public class OfyService implements ServletContextListener {

  /** Define all entities first. */
  static {
    ObjectifyService.register(Project.class);
    ObjectifyService.register(Technology.class);
    ObjectifyService.register(Endorsement.class);
    ObjectifyService.register(TechGalleryUser.class);
    ObjectifyService.register(Skill.class);
    ObjectifyService.register(TechnologyComment.class);
    ObjectifyService.register(TechnologyLink.class);
    ObjectifyService.register(TechnologyRecommendation.class);
    ObjectifyService.register(UserProfile.class);
    ObjectifyService.register(TechnologyFollowers.class);
    ObjectifyService.register(EmailNotification.class);
    ObjectifyService.register(CronJob.class);
    ObjectifyService.register(ApplicationConfiguration.class);
  }

  /**
   * Method that returns the objectify service reference.
   *
   * @return Objectify.
   */
  public static Objectify ofy() {
    return ObjectifyService.ofy();
    // prior to v.4.0 use .begin() ,
    // since v.4.0 use ObjectifyService.ofy();
  }

  /**
   * Method that returns the objectify factory reference.
   *
   * @return Objectify.
   */
  public static ObjectifyFactory factory() {
    return ObjectifyService.factory();
  }

  /**
   * Context destroyed for http servlet.
   */
  @Override
  public void contextDestroyed(ServletContextEvent arg0) {}

  /**
   * Context initialized for http servlet.
   */
  @Override
  public void contextInitialized(ServletContextEvent arg0) {
    // Register entities = done with static block
  }

}
