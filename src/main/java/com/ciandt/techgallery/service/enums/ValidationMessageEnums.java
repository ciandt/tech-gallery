package com.ciandt.techgallery.service.enums;

/**
 * Enum for mapping messages.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public enum ValidationMessageEnums {

  // Message for skills
  SKILL_CANNOT_BLANK("Skill cannot be null."), SKILL_RANGE("Skill's value must be between 1 and 5"),
  // Message for technologies
  TECHNOLOGY_ID_CANNOT_BLANK("Technology or technology's id cannot be null or blank."), TECHNOLOGY_NOT_EXIST(
      "Technology doesn't exist."),
  // Message for users
  USER_CANNOT_BLANK("User or user's id cannot be null or blank."), USER_NOT_EXIST(
      "User doesn't exist."), USER_GOOGLE_ENDPOINT_NULL("A user must be sent to endpoints.");

  private String message;

  ValidationMessageEnums(String message) {
    this.message = message;
  }

  public String message() {
    return message;
  }

}
