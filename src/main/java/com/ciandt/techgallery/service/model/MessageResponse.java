package com.ciandt.techgallery.service.model;

import com.google.api.server.spi.ServiceException;

import com.ciandt.techgallery.utils.i18n.I18n;

/**
 * Response entity with custom message.
 * 
 * @author felipers
 *
 */
@SuppressWarnings("serial")
public class MessageResponse extends ServiceException implements Response {

  /** message to be returned. */
  private String msg;
  private static I18n i18n = I18n.getInstance();

  /**
   * Constructor with http code and custom message.
   * 
   * @param httpcode http status.
   * @param msg custom message.
   */
  public MessageResponse(int httpcode, String msg) {
    super(httpcode, i18n.t(msg));
    this.setMsg(i18n.t(msg));
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
