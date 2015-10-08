package com.ciandt.techgallery.service.enums;

import com.ciandt.techgallery.utils.i18n.I18n;

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
  TECHNOLOGY_ID_CANNOT_BLANK(
      "Technology or technology's id cannot be null or blank."), TECHNOLOGY_NOT_EXIST(
          "Technology doesn't exist."),
          // Message for users
          USER_CANNOT_BLANK("User or user's id cannot be null or blank."), USER_NOT_EXIST(
              "User doesn't exist."), USER_GOOGLE_ENDPOINT_NULL(
                  "A user must be sent to endpoints."),
                  // Message for Comments
                  COMMENT_CANNOT_BLANK("Comment cannot be null or blank."), COMMENT_MUST_BE_LESSER(
                      "Comment must have less than 500 characters."), COMMENT_NOT_EXIST(
                          "Comment doesn't exist."), COMMENT_ID_CANNOT_BLANK(
                              "Comment or comment's id cannot be null or blank."), COMMENT_AUTHOR_ERROR(
                                  "This comment doesn't belong to this user."), RECOMMEND_ID_CANNOT_BLANK(
                                      "Recommend or recommend's id cannot be null or blank."), RECOMMEND_NOT_EXIST(
                                          "Recommend doesn't exist."), RECOMMEND_RECOMMENDER_ERROR(
                                              "This recommend doesn't belong to this user."), TO_INACTIVE(
                                                  "Inactive transient object is not allowed."), NO_TECHNOLOGY_WAS_FOUND(
                                                      "No technology was found."), TECHNOLOGY_ID_CANNOT_BE_BLANK(
                                                          "Technology's id cannot be blank.");


  private String message;
  private final I18n i18n = I18n.getInstance();

  ValidationMessageEnums(String message) {
    this.message = i18n.t(message);
  }

  public String message() {
    return message;
  }

}
