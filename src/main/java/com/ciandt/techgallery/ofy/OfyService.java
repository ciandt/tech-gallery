package com.ciandt.techgallery.ofy;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ciandt.techgallery.persistence.model.Card;
import com.ciandt.techgallery.persistence.model.UserGroup;
import com.ciandt.techgallery.sample.persistence.model.Recommendation;
import com.ciandt.techgallery.sample.persistence.model.Sample;
import com.ciandt.techgallery.sample.persistence.model.Technology;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Service class for Objectify settings.
 * 
 * @author felipers
 *
 */
public class OfyService implements ServletContextListener {

  /** Define all entities first. */
  static {
    // ObjectifyService.register(User.class);
    ObjectifyService.register(UserGroup.class);
    ObjectifyService.register(Card.class);
    ObjectifyService.register(Technology.class);
    ObjectifyService.register(Recommendation.class);
    ObjectifyService.register(Sample.class);
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
  public void contextDestroyed(ServletContextEvent arg0) {
    // TODO Auto-generated method stub
  }

  /**
   * Context initialized for http servlet.
   */
  @Override
  public void contextInitialized(ServletContextEvent arg0) {
    // Register entities = done with static block
  }

}
