package com.ciandt.techgallery.service.enums;

/**
 * Enum for mapping recommendations.
 * 
 * @author Thulio Ribeiro
 *
 */
public enum RecommendationEnums {

  ANY("Todos"), DISCUSS_NEXT("Discutir para próxima"), 
  NOT_RECOMMENDED_RETIRED("Não recomendadas ou aposentar"), 
  OBSERVED_CONCEPT_TEST("Observar e fazer provas de conceito"), 
  RECOMMENDED("Recomendada"), 
  RECOMMENDED_ALTERNATIVE("Recomendada alternativa"), 
  USE_LEARN("Usar e aprender"), 
  UNINFORMED("Não informado");


  private String message;

  private RecommendationEnums(String message) {
    this.message = message;
  }

  public String message() {
    return message;
  }

}
