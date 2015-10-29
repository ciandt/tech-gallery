package com.ciandt.techgallery.service.enums;

/**
 * Enum to set email configurations.
 * 
 * @author Thulio Ribeiro
 *
 */
public enum EmailTypeEnum {

  DAILY_RESUME_MAIL("[Tech Gallery] Resumo diário sobre ", "TechnologyActivitiesResumeEmailTemplate", "Resumo do dia para os followers"), 
  ENDORSED_MAIL("[Tech Gallery] Você foi indicado em ", "UserEndorsermentEmailTemplate", "Notificação de indicação");

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
