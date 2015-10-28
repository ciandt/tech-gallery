package com.ciandt.techgallery.service.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;

/**
 * Used for sending emails.
 * 
 * @author bliberal
 *
 */
public class TechnologyActivitiesTO {

  TechGalleryUser user;
  TechGalleryUser endorserUser;
  Technology technology;
  List<TechnologyComment> comments;
  List<TechnologyRecommendation> recommendations;
  Date timestamp;
  String appName;

  public TechGalleryUser getUser() {
    return user;
  }

  public void setUser(TechGalleryUser user) {
    this.user = user;
  }

  public Technology getTechnology() {
    return technology;
  }

  public TechGalleryUser getEndorserUser() {
    return endorserUser;
  }

  public void setEndorserUser(TechGalleryUser endorserUser) {
    this.endorserUser = endorserUser;
  }

  public void setTechnology(Technology technology) {
    this.technology = technology;
  }

  public Boolean getHasComments() {
    return !this.comments.isEmpty();
  }

  public List<TechnologyComment> getComments() {
    return comments;
  }

  public void setComments(List<TechnologyComment> comments) {
    this.comments = comments;
  }

  public Boolean getHasRecommendations() {
    return !this.recommendations.isEmpty();
  }

  public List<TechnologyRecommendation> getRecommendations() {
    return recommendations;
  }

  public void setRecommendations(List<TechnologyRecommendation> recommendations) {
    this.recommendations = recommendations;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getFormattedTimestamp() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    return formatter.format(this.timestamp);
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
