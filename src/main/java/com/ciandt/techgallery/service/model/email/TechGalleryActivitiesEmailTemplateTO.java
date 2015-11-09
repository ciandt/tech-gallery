package com.ciandt.techgallery.service.model.email;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.utils.TechGalleryUtil;
import com.ciandt.techgallery.utils.timezone.TimezoneManager;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * TO used for sending emails with mustache template.
 * 
 * @author bliberal
 *
 */
public class TechGalleryActivitiesEmailTemplateTO {

  TechGalleryUser follower;
  List<TechnologyActivitiesEmailTemplateTO> technologyActivitiesTo;
  Date timestamp;
  String appName;
  String techgalleryLink;

  public TechGalleryActivitiesEmailTemplateTO(String applicationName, TechGalleryUser followerUser,
      List<TechnologyActivitiesEmailTemplateTO> activitiesList) {
    super();
    this.timestamp = new Date();
    this.appName = applicationName;
    this.follower = followerUser;
    this.technologyActivitiesTo = activitiesList;
  }
  
  public TechGalleryActivitiesEmailTemplateTO() {
  }
  
  public TechGalleryUser getFollower() {
    return follower;
  }

  public void setFollower(TechGalleryUser follower) {
    this.follower = follower;
  }

  public List<TechnologyActivitiesEmailTemplateTO> getTechnologyActivitiesTo() {
    return technologyActivitiesTo;
  }

  public void setTechnologyActivitiesTo(
      List<TechnologyActivitiesEmailTemplateTO> technologyActivitiesTo) {
    this.technologyActivitiesTo = technologyActivitiesTo;
  }
  
  public String getFormattedTimestamp() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    return formatter.format(TimezoneManager.getInstance().convertToUserTimezone(this.timestamp));
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }
  
  /**
   * Get link to application page according to runtime enviroment. Ex.: localhost, version-dot-.
   * 
   * @return link to application page.
   */
  public String getTechgalleryLink() {
    String environment = System.getProperty(Constants.RUNTIME_ENVIRONMENT_PROPERTY);
    if (StringUtils.equals(Constants.PRODUCTION_PROPERTY, environment)) {
      String applicationId = System.getProperty(Constants.APPLICATION_ID_PROPERTY);
      return "https://" + TechGalleryUtil.getApplicationVersion() + "-dot-" + applicationId + ".appspot.com/";
    } else {
      return Constants.LINK_LOCALHOST;
    }
  }
  
}
