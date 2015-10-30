package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.utils.TechGalleryUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Used for sending emails.
 * 
 * @author bliberal
 *
 */
public class TechnologyActivitiesTO {

  TechGalleryUser endorserUser;
  Technology technology;
  List<TechnologyComment> comments;
  List<TechnologyRecommendation> recommendations;
  String technologyLink;

  /**
   * Get link to view technology page according to runtime enviroment. Ex.: localhost, version-dot-.
   * 
   * @return link to technology page.
   */
  public String getTechnologyLink() {
    String linkTechnology;
    String queryString = "?id=" + technology.getId();
    String environment = System.getProperty(Constants.RUNTIME_ENVIRONMENT_PROPERTY);
    if (StringUtils.equals(Constants.PRODUCTION_PROPERTY, environment)) {
      String applicationId = System.getProperty(Constants.APPLICATION_ID_PROPERTY);
      linkTechnology = "https://" + TechGalleryUtil.getApplicationVersion() + "-dot-" + applicationId + ".appspot.com/";
    } else {
      linkTechnology = Constants.LINK_LOCALHOST;
    }
    return linkTechnology + Constants.PATH_VIEW_TECH_HTML + queryString;
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
    return this.comments != null ? !this.comments.isEmpty() : false;
  }

  public List<TechnologyComment> getComments() {
    return comments;
  }

  public void setComments(List<TechnologyComment> comments) {
    this.comments = comments;
  }

  public Boolean getHasRecommendations() {
    return this.recommendations != null ? !this.recommendations.isEmpty() : false;
  }

  public List<TechnologyRecommendation> getRecommendations() {
    return recommendations;
  }

  public void setRecommendations(List<TechnologyRecommendation> recommendations) {
    this.recommendations = recommendations;
  }
}
