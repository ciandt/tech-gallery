package com.ciandt.techgallery.service.model;

public class UserSkillTO implements Response {

  private String email;
  private String[] techSkill;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String[] getTechSkill() {
    return techSkill;
  }

  public void setTechSkill(String[] techSkill) {
    this.techSkill = techSkill;
  }

}
