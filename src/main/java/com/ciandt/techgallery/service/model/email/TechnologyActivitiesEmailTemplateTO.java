package com.ciandt.techgallery.service.model.email;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.utils.TechGalleryUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * TO used for sending emails with mustache template.
 * 
 * @author bliberal
 *
 */
public class TechnologyActivitiesEmailTemplateTO {

  StringBuilder endorsersList;
  Technology technology;
  List<TechnologyComment> comments;
  List<TechnologyRecommendation> recommendations;
  String technologyLink;
  String context;
  String endorsers;

  public TechnologyActivitiesEmailTemplateTO(String endorsers, Technology technology,
      String context, List<TechnologyComment> comments,
      List<TechnologyRecommendation> recommendations, String technologyLink) {
    super();
    this.endorsers = endorsers;
    this.technology = technology;
    this.context = context;
    this.comments = comments;
    this.recommendations = recommendations;
    this.technologyLink = technologyLink;
    this.endorsersList = new StringBuilder(endorsers);
  }

  public TechnologyActivitiesEmailTemplateTO() {}

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
      linkTechnology = "https://" + TechGalleryUtil.getApplicationVersion() + "-dot-"
          + applicationId + ".appspot.com/";
    } else {
      linkTechnology = Constants.LINK_LOCALHOST;
    }
    return linkTechnology + Constants.PATH_VIEW_TECH_HTML + queryString;
  }

  public Technology getTechnology() {
    return technology;
  }

  public String getEndorsers() {
    return endorsers;
  }

  public void setEndorsers(String endorsers) {
    this.endorsers = endorsers;
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

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  /**
   * Method that appends an endorser name to the existing 'set' of endorsers.
   * 
   * @param endorserName name to be added.
   * @param last flag for differing ',' or 'e'.
   */
  public void addEndorser(String endorserName, boolean last) {
    if (last) {
      endorsersList.append(" e ").append(endorserName);
      endorsers = endorsersList.toString();
    } else {
      endorsersList.append(", ").append(endorserName);
    }
  }

}
