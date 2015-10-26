package com.ciandt.techgallery.service.endpoint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.service.SocialNetworkCommunicationService;
import com.ciandt.techgallery.service.enums.FeatureEnum;
import com.ciandt.techgallery.service.impl.GooglePlusCommunicationServiceImpl;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

/**
 * Endpoint controller class for Social networks communications requests.
 * 
 * @author Thulio Ribeiro
 *
 */
@Api(name = "rest", version = "v1", clientIds = { Constants.WEB_CLIENT_ID,
    Constants.API_EXPLORER_CLIENT_ID }, scopes = { Constants.EMAIL_SCOPE, Constants.PLUS_SCOPE,
        Constants.PLUS_STREAM_WRITE })
public class SocialNetworkCommunicationEndpoint {

  private SocialNetworkCommunicationService service = GooglePlusCommunicationServiceImpl.getInstance();

  /**
   * Endpoint to post the content in users Google+ according the feature
   * performed by front-end
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
   * @param req
   *          is the current http servlet request
   * @throws InternalServerErrorException
   * @throws BadRequestException
   * @throws NotFoundException
   * @throws IOException
   */
  @ApiMethod(name = "postComment", path = "googleplus/post", httpMethod = "post")
  public void postGooglePlus(@Named("feature") FeatureEnum feature, @Named("score") @Nullable Boolean score,
      @Named("comment") @Nullable String comment, @Named("currentUserMail") String currentUserMail,
      @Named("endorsedMail") @Nullable String endorsedMail, @Named("technologyName") String technologyName,
      @Named("appLink") String appLink, User user, HttpServletRequest req)
          throws InternalServerErrorException, BadRequestException, NotFoundException, IOException {
    String header = req.getHeader("Authorization");
    String accesstoken = header.substring(header.indexOf(' ')).trim();
    service.postInUserProfile(feature, score, comment, currentUserMail, endorsedMail, technologyName, appLink, user,
        accesstoken);
  }
}
