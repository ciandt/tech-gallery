package com.ciandt.techgallery.service.enums;

import com.ciandt.techgallery.Constants;

/**
 * Enum for mapping Date Filters.
 * 
 * @author Thulio Ribeiro
 *
 */
public enum FeatureEnum {

  ENDORSE(" indicou ") {

    @Override
    public String createContent(String currentUserMail, String endorsedMail, String technologyName, Boolean score,
        String comment) {
      return "+" + currentUserMail + this.message() + "+" + endorsedMail + " na tecnologia " + technologyName;
    };

  },
  COMMENT(" comentou na tecnologia ") {

    @Override
    public String createContent(String currentUserMail, String endorsedMail, String technologyName, Boolean score,
        String comment) {
      return "+" + currentUserMail + this.message() + technologyName + Constants.NEW_LINE + Constants.NEW_LINE + "\""
          + comment + "\"";
    }

  },
  RECOMMEND(" recomendou ") {

    @Override
    public String createContent(String currentUserMail, String endorsedMail, String technologyName, Boolean score,
        String comment) {
      if (score) {
        return "+" + currentUserMail + this.message() + Constants.POSITIVE_RECOMMENDATION_TEXT
            + technologyName + Constants.NEW_LINE + Constants.NEW_LINE + "\"" + comment + "\"";
      } else {
        return "+" + currentUserMail + this.message() + Constants.NEGATIVE_RECOMMENDATION_TEXT
            + technologyName + Constants.NEW_LINE + Constants.NEW_LINE + "\"" + comment + "\"";
      }
    }
  };

  private String message;

  private FeatureEnum(String message) {
    this.message = message;
  }

  /**
   * Method that create a content for the post on Social network user profile
   * 
   * @param feature
   *          the type of feature performed by user.
   * @param currentUserMail
   *          is the email of the user logged in.
   * @param endorsedMail
   *          is the email of the endorsed user in case of endorse feature.
   * @param technologyName
   *          is the name of technology performed by feature.
   * @param score
   *          is the positive or negative recommendation in case of
   *          recommendation feature.
   * @return
   */
  public abstract String createContent(String currentUserMail, String endorsedMail, String technologyName,
      Boolean score, String comment);

  public String message() {
    return message;
  }

}
