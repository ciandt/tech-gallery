package com.ciant.techgallery.sample.services.model.enums;

public enum ResponseMensagem {

  ERRO("Erro"), OK("Ok");

  String descricao;

  private ResponseMensagem(String descricao) {
    this.descricao = descricao;
  }

  public String descricao() {
    return descricao;
  }
}
