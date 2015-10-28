package com.ciandt.techgallery.service.email;

import com.ciandt.techgallery.service.model.TechnologyActivitiesTO;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

  private String rule;

  private String reason;
  
  private String resource;
  
  private TechnologyActivitiesTO techActivities;
  
  /**
   * Variable and value to be replaced in template. 
   */
  private Map<String, String> dynamicData;

  public EmailConfig(){}
  
  
  /**
   * Default constructor if email does not use template email feature.
   */
  public EmailConfig(String subject, String body, String rule,
      String reason, String ... to) {
    
    this.to = to;
    this.subject = subject;
    this.body = body;
    this.rule = rule;
    this.reason = reason;
  }

  /**
   * Default constructor if email use template email feature.
   * You must supply resource and dynamicData fields.
   */
  public EmailConfig(String subject, String resource, Map<String, String> dynamicData, String rule, String reason, String ... to) {
    
    this.to = to;
    this.subject = subject;
    this.rule = rule;
    this.reason = reason;
    this.resource = resource;
    this.dynamicData = dynamicData;
    if (getResource() == null) {
      throw new IllegalArgumentException("Resource field is required.");
    }
  }

  public EmailConfig(String subject, String resource, TechnologyActivitiesTO techActivities,
      String rule, String reason, String... to) {
    this.to = to;
    this.subject = subject;
    this.rule = rule;
    this.reason = reason;
    this.resource = resource;
    this.techActivities = techActivities;
    if (getResource() == null) {
      throw new IllegalArgumentException("Resource field is required.");
    }
  }


  public String[] getTo() {
    return to;
  }

  public EmailConfig to(String ... to) {
    this.to = to;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public EmailConfig subject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getBody() {
    return body;
  }

  public EmailConfig body(String body) {
    this.body = body;
    return this;
  }

  public String getRule() {
    return rule;
  }

  public EmailConfig rule(String rule) {
    this.rule = rule;
    return this;
  }

  public String getReason() {
    return reason;
  }

  public EmailConfig reason(String reason) {
    this.reason = reason;
    return this;
  }

  public Map<String, String> getDynamicData() {
    return dynamicData;
  }

  /**
   * Adds variable and value to be overwritten
   * in the resource content.
   *  
   * @param variable - variable in the resource including prefixes 
   * @param value - value to be overwritten
   * @return dynamic map to be used inline.
   */
  public Map<String, String> putVariableAndValue(String variable, String value) {
    if (dynamicData == null) {
      dynamicData = new HashMap<String, String>();
    }
    dynamicData.put(variable, value);
    return dynamicData;
  }
  
  public EmailConfig setDynamicData(Map<String, String> dynamicData) {
    this.dynamicData = dynamicData;
    return this;
  }

  public String getResource() {
    return resource;
  }

  public void resource(String resource) {
    this.resource = resource;
  }
  
  /**
   * Must be called if email is a template email.
   * Resource and dynamic data must not be null.
   */
  public void processTemplate() {
    if (resource != null) {
      MustacheFactory mf = new DefaultMustacheFactory();
      Mustache mustache = mf.compile(this.resource);
  
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Writer writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
      try {
        mustache.execute(writer, techActivities).flush();
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
