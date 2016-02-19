package com.ciandt.techgallery.service;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.model.email.TechnologyActivitiesEmailTemplateTO;

import java.util.List;

/**
 * Services for Recommendations.
 *
 */
public interface TechnologyActivitiesService {

  /**
   * Create a TechnologyActivitiesTO used in daily resume email.
   * 
   * @param technology.
   * @param dailyRecommendations.
   * @param dailyComments.
   * @return TechnologyActivitiesTO.
   */
  TechnologyActivitiesEmailTemplateTO createTechnologyActivitiesTo(Technology technology,
      List<TechnologyRecommendation> dailyRecommendations, List<TechnologyComment> dailyComments);

}
