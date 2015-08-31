package com.ciandt.techgallery.service.model;

import com.google.api.server.spi.ServiceException;

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

  /**
   * Constructor with http code and custom message.
   * 
   * @param httpcode http status.
   * @param msg custom message.
   */
  public MessageResponse(int httpcode, String msg) {
    super(httpcode, msg);
    this.setMsg(msg);
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
