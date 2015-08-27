package com.ciandt.techgallery.sample.service.model;

public enum ResponseMessageEnum {

  ERRO("Erro"), OK("Ok");

  String descricao;

  private ResponseMessageEnum(String descricao) {
    this.descricao = descricao;
  }

  public String descricao() {
    return descricao;
  }
}
