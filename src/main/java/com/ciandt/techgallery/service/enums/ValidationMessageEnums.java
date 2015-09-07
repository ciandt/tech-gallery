package com.ciandt.techgallery.service.enums;

/**
 * Enum for mapping messages.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public enum ValidationMessageEnums {

  // Message for skills
  SKILL_CANNOT_BLANK("Skill cannot be null."), 
  SKILL_RANGE("Skill's value must be between 1 and 5"),
  // Message for technologies
  TECHNOLOGY_ID_CANNOT_BLANK("Technology(id) cannot be null or blank."), 
  TECHNOLOGY_NOT_EXIST("Technology doesn't exist."),
  // Message for users
  USER_CANNOT_BLANK("User(id) cannot be null or blank."), 
  USER_NOT_EXIST("User doesn't exist.");

  private String message;

  ValidationMessageEnums(String message) {
    this.message = message;
  }

  public String message() {
    return message;
  }

}
