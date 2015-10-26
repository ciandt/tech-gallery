package com.ciandt.techgallery.service.enums;

/**
 * Enum for mapping Date Filters.
 * 
 * @author Thulio Ribeiro
 *
 */
public enum FeatureEnum {

  ENDORSE(" indicou "), COMMENT(" comentou na tecnologia "), RECOMMEND(" recomendou ");

  private String message;

  private FeatureEnum(String message) {
    this.message = message;
  }

  public String message() {
    return message;
  }

}
