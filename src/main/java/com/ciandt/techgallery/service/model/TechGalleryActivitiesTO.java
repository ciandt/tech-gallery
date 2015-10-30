package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Used for sending emails.
 * 
 * @author bliberal
 *
 */
public class TechGalleryActivitiesTO {

  TechGalleryUser follower;
  List<TechnologyActivitiesTO> technologyActivitiesTo;
  Date timestamp;
  String appName;

  public TechGalleryUser getFollower() {
    return follower;
  }

  public void setFollower(TechGalleryUser follower) {
    this.follower = follower;
  }

  public List<TechnologyActivitiesTO> getTechnologyActivitiesTo() {
    return technologyActivitiesTo;
  }

  public void setTechnologyActivitiesTo(List<TechnologyActivitiesTO> technologyActivitiesTo) {
    this.technologyActivitiesTo = technologyActivitiesTo;
  }
  
  public String getFormattedTimestamp() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    return formatter.format(this.timestamp);
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
  
}
