package com.ciandt.techgallery.service.enums;

/**
 * Enum for mapping Order Options.
 * 
 * @author Felipe Ibrahim
 *
 */
public enum TechnologyOrderOptionEnum {

  POSITIVE_RECOMENDATION_QUANTITY("Quantidade de Recomendações Positivas"),
  NEGATIVE_RECOMENDATION_QUANTITY("Quantidade de Recomendações Negativas"),
  COMENTARY_QUANTITY("Quantidade de Comentários"),
  ENDORSEMENT_QUANTITY("Quantidade de Indicações");
  
  private String option;

  TechnologyOrderOptionEnum(String option) {
    this.option = option;
  }

  public String option() {
    return option;
  }

  public static TechnologyOrderOptionEnum fromString(String text) {
    if (text != null) {
      for (TechnologyOrderOptionEnum item : TechnologyOrderOptionEnum.values()) {
        if (text.equalsIgnoreCase(item.option())) {
          return item;
        }
      }
    }
    return null;
  }
}
