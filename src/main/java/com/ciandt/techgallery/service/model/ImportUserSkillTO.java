package com.ciandt.techgallery.service.model;

public class ImportUserSkillTO implements Response {

  UserSkillTO[] userSkill;

  public UserSkillTO[] getUserSkill() {
    return userSkill;
  }

  public void setUserSkill(UserSkillTO[] userSkill) {
    this.userSkill = userSkill;
  }
}
