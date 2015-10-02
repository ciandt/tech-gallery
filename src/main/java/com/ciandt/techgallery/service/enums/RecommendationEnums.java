package com.ciandt.techgallery.service.enums;

import com.ciandt.techgallery.utils.i18n.I18n;

/**
 * Enum for mapping recommendations.
 * 
 * @author Thulio Ribeiro
 *
 */
public enum RecommendationEnums {
  
  ANY("Any"),
  RECOMMENDED("Recommended"),
  NOT_RECOMMENDED_RETIRED("Not recommended or retire"),
  RECOMMENDED_ALTERNATIVE("Recommended as alternative"),
  USE_LEARN("Use and learn"),
  OBSERVED_CONCEPT_TEST("Observe and make concept tests"),
  DISCUSS_NEXT("Discuss for next"),
  UNINFORMED("Not informed");


  private String message;
  private I18n i18n  = I18n.getInstance();
  
  private RecommendationEnums(String message) {
    this.message = i18n.t(message);
  }

  public String message() {
    return message;
  }

}
