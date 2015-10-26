package com.ciandt.techgallery.service.enums;

/**
 * Enum for mapping Date Filters.
 * 
 * @author Thulio Ribeiro
 *
 */
public enum DateFilterEnum {

  LAST_DAY("Último Dia"), LAST_7_DAYS("Últimos sete dias"), LAST_30_DAYS("Últimos trinta dias");

  private String message;

  private DateFilterEnum(String message) {
    this.message = message;
  }

  public String message() {
    return message;
  }

}
