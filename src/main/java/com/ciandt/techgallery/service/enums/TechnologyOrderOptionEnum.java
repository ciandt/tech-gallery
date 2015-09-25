package com.ciandt.techgallery.service.enums;

import com.ciandt.techgallery.utils.i18n.I18n;

public enum TechnologyOrderOptionEnum {

  POSITIVE_RECOMENDATION_QUANTITY(1, "Positive Recomendations Quantity"),
  NEGATIVE_RECOMENDATION_QUANTITY(2, "Negative Recomendations Quantity"),
  COMENTARY_QUANTITY(3, "Comentaries Quantity"),
  ENDORSEMENT_QUANTITY(4, "Endorsed Quantity");
  
  private int id; 
  private String option;
  private I18n i18n  = I18n.getInstance();
  
  TechnologyOrderOptionEnum(int id, String option) {
    this.id = id;
    this.option = i18n.t(option);
  }

  public int id() {
    return id;
  }
  
  public String option() {
    return option;
  }
  
}
