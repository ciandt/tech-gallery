package com.ciandt.techgallery.service.enums;

/**
 * Enum for mapping recommendations.
 * 
 * @author Thulio Ribeiro
 *
 */
public enum RecommendationEnums {

  ANY("Todos"), 
  RECOMMENDED("Recomendada"), 
  RECOMMENDED_ALTERNATIVE("Recomendada alternativa"), 
  USE_LEARN("Usar e aprender"), 
  OBSERVED_CONCEPT_TEST("Observar e fazer provas de conceito"), 
  NOT_RECOMMENDED_RETIRED("Não recomendadas ou aposentar"), 
  UNINFORMED("Não informado"); 


  private String message;

  private RecommendationEnums(String message) {
    this.message = message;
  }

  public String message() {
    return message;
  }

}
