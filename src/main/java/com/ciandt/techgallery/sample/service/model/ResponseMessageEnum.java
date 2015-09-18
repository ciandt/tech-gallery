package com.ciandt.techgallery.sample.service.model;

public enum ResponseMessageEnum {

  ERRO("Error"), OK("Ok");

  String description;

  private ResponseMessageEnum(String description) {
    this.description = description;
  }

  public String description() {
    return description;
  }
}
