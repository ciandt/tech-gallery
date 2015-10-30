package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;

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

  public TechnologyActivitiesTO() {
    this.technologyLink = generateTechnologyLink();
  }
  
  public String getTechnologyLink() {
    return this.technologyLink;
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
  
  /**
   * Get link to view technology page according to runtime enviroment. Ex.: localhost, version-dot-.
   * 
   * @return link to technology page.
   */
  public String generateTechnologyLink() {
    String linkTechnology;
    String queryString = "?id=" + technology.getId();
    String environment = System.getProperty(Constants.RUNTIME_ENVIRONMENT_PROPERTY);
    if (StringUtils.equals(Constants.PRODUCTION_PROPERTY, environment)) {
      String applicationId = System.getProperty(Constants.APPLICATION_ID_PROPERTY);
      String version = System.getProperty(Constants.APPLICATION_VERSION_PROPERTY);
      String versionName = version.split("\\.")[0];
      linkTechnology = "https://" + versionName + "-dot-" + applicationId + ".appspot.com/";
    } else {
      linkTechnology = Constants.LINK_LOCALHOST;
    }
    return linkTechnology + Constants.PATH_VIEW_TECH_HTML + queryString;
  }
}
