package com.ciandt.techgallery.service.email;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.enums.EmailTypeEnum;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Email configuration object.
 * 
 * @author bcarneiro
 *
 */
public class EmailConfig {

  private String[] to;

  private String subject;

  private String body;

  private String reason;

  private String template;

  /**
   * Default constructor if email does not use template email feature.
   */
  public EmailConfig(String subject, String body, String reason, String... to) {
    this.to = to;
    this.subject = subject;
    this.body = body;
    this.reason = reason;
  }

  /**
   * Constructor used in sending emails with mustache templates.
   * 
   * @param EnumEmailType.
   * @param mustacheTo - Object to be passed to mustache to fill the template.
   * @param to - receiver.
   */
  public EmailConfig(EmailTypeEnum emailType, String subject, Object mustacheTo, String... to) {
    this.template = emailType.getTemplate();
    this.reason = emailType.getReason();
    this.subject = subject;
    this.to = to;
    processTemplate(mustacheTo);
  }

  public String[] getTo() {
    return to;
  }

  public void setTo(String[] to) {
    this.to = to;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  /**
   * Must be called if email is a template email. Resource and dynamic data must not be null.
   */
  public void processTemplate(Object mustacheTo) {
    if (template != null) {
      MustacheFactory mf = new DefaultMustacheFactory();
      Mustache mustache = mf.compile(Constants.TEMPLATES_FOLDER + File.separator + this.template);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Writer writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
      try {
        mustache.execute(writer, mustacheTo).flush();
      } catch (IOException e) {
        throw new RuntimeException("Could not mount template file");
      }
      try {
        this.body = new String(baos.toByteArray(), "UTF-8");
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException("Could not encode template file");
      }
    }
  }

}
