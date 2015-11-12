package com.ciandt.techgallery.service.enums;


/**
 * Enum to set email configurations.
 * 
 * @author Thulio Ribeiro
 *
 */
public enum EmailTypeEnum {

  DAILY_RESUME("[Tech Gallery] Resumo diário - ",
      "TechnologyActivitiesResume",
      "Resumo do dia para os followers"),
  ENDORSED("[Tech Gallery] Resumo diário de Indicação - ",
      "UserEndorserment",
      "Resumo do dia para indicações");

  private String subject;
  private String template;
  private String reason;

  private EmailTypeEnum(String subject, String template, String reason) {
    this.subject = subject;
    this.template = template;
    this.reason = reason;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

}
