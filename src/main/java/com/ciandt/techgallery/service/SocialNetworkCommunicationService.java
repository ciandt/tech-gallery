package com.ciandt.techgallery.service;

import java.io.IOException;

import com.ciandt.techgallery.service.enums.FeatureEnum;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

/**
 * Services for google plus communications.
 * 
 * @author Thulio Ribeiro
 *
 */
public interface SocialNetworkCommunicationService {

  /**
   * Service called by endpoint to post the content in users social network
   * according the feature performed by front-end
   * 
   * @param feature
   *          is the feature performed by front-end
   * @param score
   *          is the positive or negative recommendation in case of
   *          recommendation feature
   * @param currentUserMail
   *          is the email of the user logged in.
   * @param endorsedMail
   *          is the email of the endorsed user in case of endorse feature.
   * @param technologyName
   *          is the name of technology performed by feature.
   * @param appLink
   *          is the link to the page that made a endpoint call.
   * @param comment
   *          is the comment in case of comment feature.
   * @param user
   *          is the user logged in
   * @param accessToken
   *          is the access token passed by endpoint
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws IOException
   */
  void postInUserProfile(FeatureEnum feature, Boolean score, String comment, String currentUserMail,
      String endorsedMail, String technologyName, String techgalleryLink, User user, String accessToken)
          throws InternalServerErrorException, BadRequestException, NotFoundException, IOException;

}
