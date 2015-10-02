package com.ciandt.techgallery.service.enums;

import com.ciandt.techgallery.utils.i18n.I18n;

/**
 * Enum for mapping Order Options.
 * 
 * @author Felipe Ibrahim
 *
 */
public enum TechnologyOrderOptionEnum {

  POSITIVE_RECOMENDATION_QUANTITY("Positive Recomendations Quantity"),
  NEGATIVE_RECOMENDATION_QUANTITY("Negative Recomendations Quantity"),
  COMENTARY_QUANTITY("Comentaries Quantity"),
  ENDORSEMENT_QUANTITY("Endorsed Quantity");
  
  private String option;
  private I18n i18n  = I18n.getInstance();
  
  TechnologyOrderOptionEnum(String option) {
    this.option = i18n.t(option);
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
