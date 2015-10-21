package com.ciandt.techgallery.service.email;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  
  private String transferId;

  private String rule;

  private String reason;
  
  private String resource;
  
  /**
   * Variable and value to be replaced in template. 
   */
  private Map<String, String> dynamicData;

  public EmailConfig(){}
  
  
  /**
   * Default constructor if email does not use template email feature.
   */
  public EmailConfig(String subject, String body, String transferId, String rule,
      String reason, String ... to) {
    
    this.to = to;
    this.subject = subject;
    this.body = body;
    this.transferId = transferId;
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
    processTemplate();
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

  public String getTransferId() {
    return transferId;
  }

  public EmailConfig transferId(String transferId) {
    this.transferId = transferId;
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
      try {
        String bodyTemplate = 
            IOUtils.toString(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resource), "UTF-8");
        
        if (bodyTemplate != null && dynamicData != null && !dynamicData.isEmpty()) {
          
          for (Entry<String, String> keyValue : dynamicData.entrySet()) {
            String value = keyValue.getValue();
            bodyTemplate =
                bodyTemplate.replaceAll(Pattern.quote(keyValue.getKey()).toString(),
                    value == null ? "" : Matcher.quoteReplacement(value));
          }
        }
        this.body = bodyTemplate;
        
      } catch (IOException io) {
        throw new RuntimeException("Could not find resource file");
      }
    }   
  }
  
}
