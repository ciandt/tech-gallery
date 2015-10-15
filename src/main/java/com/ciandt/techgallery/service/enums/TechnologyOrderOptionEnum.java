package com.ciandt.techgallery.service.enums;

/**
 * Enum for mapping Order Options.
 * 
 * @author Felipe Ibrahim
 *
 */
public enum TechnologyOrderOptionEnum {

  POSITIVE_RECOMMENDATION_AMOUNT("Quantidade de Recomendações Positivas"), 
  NEGATIVE_RECOMMENDATION_AMOUNT("Quantidade de Recomendações Negativas"), 
  COMMENT_AMOUNT("Quantidade de Comentários"), 
  ENDORSEMENT_AMOUNT("Quantidade de Indicações");

  private String option;

  TechnologyOrderOptionEnum(String option) {
    this.option = option;
  }

  public String option() {
    return option;
  }

  /**
   * Convert the text informed to Enum.
   *
   * @param text to be converted.
   * 
   * @return the enum value.
   */
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
