package com.ciant.techgallery.sample.service.model;

import com.ciant.techgallery.sample.services.model.enums.ResponseMensagem;

public class Response {
  
  ResponseMensagem mensagem;

  public ResponseMensagem getMensagem() {
    return mensagem;
  }

  public void setMensagem(ResponseMensagem mensagem) {
    this.mensagem = mensagem;
  }
}
