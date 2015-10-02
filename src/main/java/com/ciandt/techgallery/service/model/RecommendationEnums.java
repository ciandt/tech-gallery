package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.utils.i18n.I18n;

/**
 * Enum for mapping recommendations.
 * 
 * @author Thulio Ribeiro
 *
 */
public enum RecommendationEnums {
  
  ANY("Todos"),
  RECOMMENDED("Recomendada"),
  NOT_RECOMMENDED_RETIRED("Não recomendadas ou aposentar"),
  RECOMMENDED_ALTERNATIVE("Recomendada alternativa"),
  USE_LEARN("Usar e aprender"),
  OBSERVED_CONCEPT_TEST("Observar e fazer provas de conceito"),
  DISCUSS_NEXT("Discutir para próxima"),
  UNINFORMED("Não informado");


  private String message;
  private I18n i18n  = I18n.getInstance();
  
  private RecommendationEnums(String message) {
    this.message = message;
  }

  public String message() {
    return message;
  }

}
