package com.ciandt.techgallery.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.GooglePlusCommunicationService;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.FeatureEnum;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.services.plusDomains.PlusDomains;
import com.google.api.services.plusDomains.model.Acl;
import com.google.api.services.plusDomains.model.Activity;
import com.google.api.services.plusDomains.model.PlusDomainsAclentryResource;
import com.google.appengine.api.users.User;

/**
 * Services for Google Plus communications Endpoint requests.
 *
 * @author Thulio Ribeiro
 *
 */
public class GooglePlusCommunicationServiceImpl implements GooglePlusCommunicationService {

  private static GooglePlusCommunicationServiceImpl instance;

  private static final String POSITIVE_RECOMMENDATION_TEXT = "positivamente a tecnologia ";
  private static final String NEGATIVE_RECOMMENDATION_TEXT = "negativamente a tecnologia ";
  private UserServiceTG userService = UserServiceTGImpl.getInstance();
  public static final String NEW_LINE = System.getProperty("line.separator");

  private GooglePlusCommunicationServiceImpl() {
  }

  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:tribeiro@ciandt.com"> Thulio Soares Ribeiro </a>
   * @since 23/10/2015
   *
   * @return GooglePlusCommunicationServiceImpl instance.
   */
  public static GooglePlusCommunicationServiceImpl getInstance() {
    if (instance == null) {
      instance = new GooglePlusCommunicationServiceImpl();
    }
    return instance;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ciandt.techgallery.service.GooglePlusCommunicationService#
   * postGooglePlus(com.ciandt.techgallery.service.enums.FeatureEnum,
   * java.lang.Boolean, java.lang.String, java.lang.String, java.lang.String,
   * com.google.appengine.api.users.User, javax.servlet.http.HttpServletRequest)
   */
  @Override
  public void postGooglePlus(FeatureEnum feature, Boolean score, String comment, String currentUserMail,
      String endorsedMail, String technologyName, String techGalleryLink, User user, HttpServletRequest req)
          throws InternalServerErrorException, BadRequestException, NotFoundException, IOException {

    validateUser(user);
    verifyRequirements(user);

    // Create a list of ACL entries
    PlusDomainsAclentryResource resource = new PlusDomainsAclentryResource();
    resource.setType("domain");

    List<PlusDomainsAclentryResource> aclEntries = new ArrayList<PlusDomainsAclentryResource>();
    aclEntries.add(resource);

    Acl acl = new Acl();
    acl.setItems(aclEntries);
    acl.setDomainRestricted(true); // Required, this does the domain restriction

    // Create a new activity object to be executed
    String content = createContent(feature, currentUserMail, endorsedMail, technologyName, score, comment);
    Activity activity = new Activity().setObject(new Activity.PlusDomainsObject().setOriginalContent(content))
        .setAccess(acl);

    // Attach the link
    Activity.PlusDomainsObject.Attachments attachment = new Activity.PlusDomainsObject.Attachments();
    attachment.setObjectType("article");
    attachment.setUrl(techGalleryLink);
    List<Activity.PlusDomainsObject.Attachments> attachments = new ArrayList();
    attachments.add(attachment); // You can also add multiple attachments to the
                                 // post
    activity.getObject().setAttachments(attachments);

    // Creating a google credential in base of the header authorization
    String header = req.getHeader("Authorization");
    String accesstoken = header.substring(header.indexOf(' ')).trim();
    GoogleCredential credential = new GoogleCredential().setAccessToken(accesstoken);

    // Create a new authorized API client according the credential
    PlusDomains plusDomains = new PlusDomains.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();

    // Execute the API request, which calls `activities.insert` for the logged
    // in user
    activity = plusDomains.activities().insert("me", activity).execute();
  }

  /**
   * This method confirm the user preference to post on your Google+ profile
   * 
   * @param user
   *          the user logged in
   * @throws NotFoundException
   * @throws BadRequestException
   * @throws InternalServerErrorException
   */
  private void verifyRequirements(User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    final TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (techUser.getPostGooglePlusPreference().equals(Boolean.FALSE)) {
      throw new BadRequestException(ValidationMessageEnums.NOT_PERMITTED_BY_USER.message());
    }
  }

  /**
   * Method that create a content for the post on Google+
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
  private String createContent(FeatureEnum feature, String currentUserMail, String endorsedMail, String technologyName,
      Boolean score, String comment) {
    String content = new String();
    switch (feature) {
    case ENDORSE:
      content = "+" + currentUserMail + feature.message() + "+" + endorsedMail + " na tecnologia " + technologyName;
      break;

    case COMMENT:
      content = "+" + currentUserMail + feature.message() + technologyName + NEW_LINE + NEW_LINE + "\"" + comment
          + "\"";
      break;

    case RECOMMEND:
      if (score) {
        content = "+" + currentUserMail + feature.message() + POSITIVE_RECOMMENDATION_TEXT + technologyName;
      } else {
        content = "+" + currentUserMail + feature.message() + NEGATIVE_RECOMMENDATION_TEXT + technologyName;
      }
      break;

    }
    return content;
  }

  /**
   * Validate the user logged in.
   *
   * @param user
   *          info about user from google
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   */
  private void validateUser(User user) throws BadRequestException, NotFoundException, InternalServerErrorException {

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    final TechGalleryUser techUser = userService.getUserByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new NotFoundException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
  }

}
