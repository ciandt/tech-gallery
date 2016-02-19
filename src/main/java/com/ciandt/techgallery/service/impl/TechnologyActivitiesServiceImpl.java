package com.ciandt.techgallery.service.impl;

import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.persistence.model.TechnologyComment;
import com.ciandt.techgallery.persistence.model.TechnologyRecommendation;
import com.ciandt.techgallery.service.TechnologyActivitiesService;
import com.ciandt.techgallery.service.model.email.TechnologyActivitiesEmailTemplateTO;

import java.util.List;

/**
 * Services for Recommendation Endpoint requests.
 *
 * @author Thulio Ribeiro
 *
 */
public class TechnologyActivitiesServiceImpl implements TechnologyActivitiesService {

  private static TechnologyActivitiesServiceImpl instance;

  private TechnologyActivitiesServiceImpl() {}

  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 07/10/2015
   *
   * @return RecommendationServiceImpl instance.
   */
  public static TechnologyActivitiesServiceImpl getInstance() {
    if (instance == null) {
      instance = new TechnologyActivitiesServiceImpl();
    }
    return instance;
  }

  /**
   * Create a TechnologyActivitiesTO used in daily resume email.
   * 
   * @param technology.
   * @param dailyRecommendations.
   * @param dailyComments.
   * @return TechnologyActivitiesTO.
   */
  @Override
  public TechnologyActivitiesEmailTemplateTO createTechnologyActivitiesTo(Technology technology,
      List<TechnologyRecommendation> dailyRecommendations, List<TechnologyComment> dailyComments) {
    TechnologyActivitiesEmailTemplateTO techActivitiesTo =
        new TechnologyActivitiesEmailTemplateTO();
    techActivitiesTo.setTechnology(technology);
    techActivitiesTo.setComments(dailyComments);
    techActivitiesTo.setRecommendations(dailyRecommendations);
    return techActivitiesTo;
  }
}