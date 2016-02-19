package com.ciandt.techgallery.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.SocialNetworkCommunicationService;
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
public class GooglePlusCommunicationServiceImpl implements SocialNetworkCommunicationService {

  private static GooglePlusCommunicationServiceImpl instance;

  private UserServiceTG userService = UserServiceTGImpl.getInstance();

  private GooglePlusCommunicationServiceImpl() {}

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
   * postGooglePlus(com.ciandt.techgallery.service.enums.FeatureEnum, java.lang.Boolean,
   * java.lang.String, java.lang.String, java.lang.String, com.google.appengine.api.users.User,
   * javax.servlet.http.HttpServletRequest)
   */
  @Override
  public void postInUserProfile(FeatureEnum feature, Boolean score, String comment,
      String currentUserMail, String endorsedMail, String technologyName, String techGalleryLink,
      User user, String accessToken)
          throws InternalServerErrorException, BadRequestException, NotFoundException, IOException {

    TechGalleryUser techUser = userService.validateUser(user);
    verifyRequirements(techUser);

    // Create a list of ACL entries
    PlusDomainsAclentryResource resource = new PlusDomainsAclentryResource();
    resource.setType("domain");

    List<PlusDomainsAclentryResource> aclEntries = new ArrayList<PlusDomainsAclentryResource>();
    aclEntries.add(resource);

    Acl acl = new Acl();
    acl.setItems(aclEntries);
    acl.setDomainRestricted(true); // Required, this does the domain restriction

    // Create a new activity object to be executed
    String content =
        feature.createContent(currentUserMail, endorsedMail, technologyName, score, comment);
    Activity activity = new Activity()
        .setObject(new Activity.PlusDomainsObject().setOriginalContent(content)).setAccess(acl);

    // Attach the link
    Activity.PlusDomainsObject.Attachments attachment =
        new Activity.PlusDomainsObject.Attachments();
    attachment.setObjectType("article");
    attachment.setUrl(techGalleryLink);
    List<Activity.PlusDomainsObject.Attachments> attachments =
        new ArrayList<Activity.PlusDomainsObject.Attachments>();
    attachments.add(attachment); // You can also add multiple attachments to the
                                 // post
    activity.getObject().setAttachments(attachments);

    // Creating a google credential in base of the header authorization
    GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);

    // Create a new authorized API client according the credential
    PlusDomains plusDomains =
        new PlusDomains.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();

    // Execute the API request, which calls `activities.insert` for the logged
    // in user
    activity = plusDomains.activities().insert("me", activity).execute();
  }

  /**
   * This method confirm the user preference to post on your Google+ profile
   * 
   * @param user the user logged in
   * @throws NotFoundException
   * @throws BadRequestException
   * @throws InternalServerErrorException
   */
  private void verifyRequirements(TechGalleryUser user)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    if (user.getPostGooglePlusPreference().equals(Boolean.FALSE)) {
      throw new BadRequestException(ValidationMessageEnums.NOT_PERMITTED_BY_USER.message());
    }
  }

}
