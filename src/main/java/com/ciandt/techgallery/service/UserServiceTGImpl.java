package com.ciandt.techgallery.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import com.googlecode.objectify.Key;

import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.UserResponse;
import com.ciandt.techgallery.service.model.UsersResponse;
import com.ciandt.techgallery.utils.i18n.I18n;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

public class UserServiceTGImpl implements UserServiceTG {

  private static final I18n i18n = I18n.getInstance();
  private static final Logger log = Logger.getLogger(UserServiceTGImpl.class.getName());
  TechGalleryUserDAO userDAO = new TechGalleryUserDAOImpl();
  private static final String PEOPLE_ENDPOINT = "https://people.cit.com.br/profile/";

  private static final String OPERATION_FAILED = "Operation failed";

  /**
   * GET for getting all users.
   */
  @Override
  public Response getUsers() throws NotFoundException {
    List<TechGalleryUser> userEntities = userDAO.findAll();
    // if user list is null, return a not found exception
    if (userEntities == null) {
      throw new NotFoundException(OPERATION_FAILED);
    } else {
      UsersResponse response = new UsersResponse();
      List<UserResponse> innerList = new ArrayList<UserResponse>();

      for (int i = 0; i < userEntities.size(); i++) {
        TechGalleryUser user = userEntities.get(i);
        UserResponse userResponseItem = new UserResponse();
        userResponseItem.setId(user.getId());
        userResponseItem.setName(user.getName());
        userResponseItem.setEmail(user.getEmail());
        userResponseItem.setPhoto(user.getPhoto());
        innerList.add(userResponseItem);
      }

      response.setUsers(innerList);
      return response;
    }
  }

  /**
   * GET for getting one user.
   */
  @Override
  public Response getUser(final Long id) throws NotFoundException {
    TechGalleryUser userEntity = userDAO.findById(id);
    // if user is null, return a not found exception
    if (userEntity == null) {
      throw new NotFoundException(i18n.t("No user was found."));
    } else {
      return createUserResponse(userEntity);
    }
  }

  /**
   * POST for adding a new user.
   * 
   * @throws BadRequestException
   */
  @Override
  public Response addUser(final UserResponse user) throws BadRequestException {
    if (!userDataIsValid(user)) {
      throw new BadRequestException(i18n.t("User's email cannot be blank."));
    } else {
      TechGalleryUser userEntity = new TechGalleryUser();
      fillTGUserData(user, userEntity);
      userDAO.add(userEntity);
      // set the id and return it
      user.setId(userEntity.getId());
      return user;
    }
  }

  /**
   * POST This method should be executed whenever a user logs in It check whether the user exists on
   * TG's datastore and create them, if not. It also checks if the user's email has been changed and
   * update it, in case it was changed.
   * 
   * @param user A Google AppEngine API user
   * @return A response with the user data as it is on TG datastore
   * @throws NotFoundException
   * @throws BadRequestException
   * @throws InternalServerErrorException
   * @throws IOException
   * @throws OAuthRequestException
   */
  @Override
  public Response handleLogin(final User user, HttpServletRequest req) throws NotFoundException,
      BadRequestException, InternalServerErrorException, IOException, OAuthRequestException {
    if (user == null) {
      throw new OAuthRequestException(i18n.t("Authorization error"));
    }
    String userEmail = user.getEmail();
    String header = req.getHeader("Authorization");
    String accesstoken = header.substring(header.indexOf(' ')).trim(); // "Bearer ".length

    GoogleCredential credential = new GoogleCredential().setAccessToken(accesstoken);
    Plus plus = new Plus.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
        .setApplicationName(i18n.t("Tech Gallery")).build();
    Person p = plus.people().get("me").execute();
    TechGalleryUser tgUser = userDAO.findByGoogleId(user.getUserId());
    // Couldn't find by googleID. Try email
    if (tgUser == null) {
      tgUser = userDAO.findByEmail(userEmail);
    }
    // Ok, we couldn't find it. Create it.
    if (tgUser == null) {
      tgUser = new TechGalleryUser();
    }
    updateUserInformation(user, p, tgUser);
    userDAO.add(tgUser);
    log.info("User " + tgUser.getName() + " added/updated");
    UserResponse uResp = (UserResponse) createUserResponse(tgUser);
    return uResp;
  }

  /**
   * Updates current Tech Gallery user information with user data found on Google
   * 
   * @param user Google user
   * @param p Google Plus person information
   * @param tgUser Tech Gallery user
   */
  private void updateUserInformation(final User user, Person p, TechGalleryUser tgUser) {
    String plusEmail = user.getEmail();
    String plusPhoto = p.getImage().getUrl();
    String plusName = p.getDisplayName();

    String currentEmail = tgUser.getEmail();
    String currentPhoto = tgUser.getPhoto();
    String currentName = tgUser.getName();

    if (tgUser.getGoogleId() == null) {
      tgUser.setGoogleId(user.getUserId());
    }
    if (currentEmail == null || !currentEmail.equals(plusEmail)) {
      tgUser.setEmail(plusEmail);
    }
    if (currentPhoto == null || currentPhoto != plusPhoto) {
      tgUser.setPhoto(plusPhoto);
    }
    if (currentName == null || currentName != plusName) {
      tgUser.setName(plusName);
    }
  }

  /**
   * Creates a response based on a TechGalleryUser entity
   * 
   * @param tgUser
   * @return the response created
   */
  private Response createUserResponse(TechGalleryUser tgUser) {
    UserResponse response = new UserResponse();
    response.setId(tgUser.getId());
    response.setName(tgUser.getName());
    response.setEmail(tgUser.getEmail());
    response.setPhoto(tgUser.getPhoto());
    return response;
  }

  /**
   * PUT for editing a user.
   * 
   * @throws BadRequestException
   */
  @Override
  public Response updateUser(final UserResponse user) throws BadRequestException {
    if (!userDataIsValid(user) && user.getId() != null) {
      throw new BadRequestException(i18n.t("User's email cannot be blank."));
    } else {
      TechGalleryUser tgCurrentUser = userDAO.findById(user.getId());
      fillTGUserData(user, tgCurrentUser);
      userDAO.update(tgCurrentUser);
      return user;
    }
  }

  private void fillTGUserData(final UserResponse user, TechGalleryUser tgUser) {
    String userName = user.getName();
    String userEmail = user.getEmail();
    String userPhoto = user.getPhoto();
    tgUser.setName(userName);
    tgUser.setEmail(userEmail);
    tgUser.setGoogleId(user.getGoogleId());
    if (userPhoto != null) {
      tgUser.setPhoto(userPhoto);
    }
  }

  /**
   * GET for getting an user by its login.
   */
  @Override
  public Response getUserByLogin(final String login) throws NotFoundException {
    TechGalleryUser userEntity = userDAO.findByLogin(login);
    if (userEntity == null) {
      throw new NotFoundException(OPERATION_FAILED);
    } else {
      return createUserResponse(userEntity);
    }
  }

  @Override
  public TechGalleryUser getUserByEmail(final String email)
      throws BadRequestException, InternalServerErrorException {
    return userDAO.findByEmail(email);
  }

  /**
   * 
   * Checks if user exists on provider, syncs with tech gallery's datastore. If user exists, adds to
   * TG's datastore (if not there). Returns the user.
   * 
   * @param userLogin userLogin
   * @return
   * @throws NotFoundException
   * @throws BadRequestException
   * @throws InternalServerErrorException
   */
  @Override
  public TechGalleryUser getUserSyncedWithProvider(final String userLogin)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    TechGalleryUser tgUser = null;
    try {
      UserResponse userResp = (UserResponse) getUserFromProvider(userLogin);
      tgUser = userDAO.findByEmail(userResp.getEmail());
      if (tgUser == null) {
        tgUser = new TechGalleryUser();
        tgUser.setEmail(userResp.getEmail());
        tgUser.setName(userResp.getName());
        Key<TechGalleryUser> key = userDAO.add(tgUser);
        tgUser.setId(key.getId());
      }
    } catch (BadRequestException e) {
      // User not found on provider
      if (e.getMessage().equals(OPERATION_FAILED)) {
        // Logs to App Engine log
        System.err.println(OPERATION_FAILED + ": " + e.getMessage());
      }
    }
    return tgUser;
  }

  /**
   * GET Calls the provider API passing a login to obtain user information
   * 
   * @param userlogin the user login to pass to the provider API
   * @throws BadRequestException if any IO exceptions occur
   * @throws InternalServerErrorException
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Response getUserFromProvider(final String userLogin)
      throws NotFoundException, BadRequestException, InternalServerErrorException {

    String fullRequest = PEOPLE_ENDPOINT + userLogin + "?format=json";
    UserResponse uResp = new UserResponse();
    try {
      InputStream resourceStream =
          UserServiceTGImpl.class.getClassLoader().getResourceAsStream("people_basic_auth.txt");

      String auth = convertStreamToString(resourceStream);

      URL url = new URL(fullRequest);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Authorization", auth);
      ObjectMapper mapper = new ObjectMapper();

      if (conn.getResponseCode() == 200) {


        Map<String, Object> providerResponse = mapper.readValue(conn.getInputStream(), Map.class);
        HashMap<String, Object> userData = (LinkedHashMap) providerResponse.get("personal_info");
        uResp.setEmail((String) userData.get("email"));
        uResp.setName((String) userData.get("name"));
      } else {
        throw new NotFoundException(i18n.t("User not found"));
      }

    } catch (JsonParseException e) {
      throw new BadRequestException(OPERATION_FAILED);

    } catch (MalformedURLException e) {
      throw new BadRequestException(e.getMessage());

    } catch (IOException e) {
      System.err.println("An internal server error ocurred!");
      System.err.println(e.getMessage());
      throw new InternalServerErrorException(i18n.t("An internal server error ocurred."));

    }
    return uResp;
  }

  private static String convertStreamToString(java.io.InputStream is) {
    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

  /**
   * Validates user data
   * 
   * @param user the user data wrapped in a UserResponse entity
   * @return true if data is valid, false otherwise
   */
  private static boolean userDataIsValid(UserResponse user) {
    // if user is null, return a bad request exception
    if (user == null) {
      return false;
    }
    String userName = user.getName();
    String userEmail = user.getEmail();
    // user name cannot be blank
    if (userName == null || userName.replaceAll("\\s", "").equals("")) {
      return false;
    }
    // user email cannot be blank
    if (userEmail == null || userEmail.replaceAll("\\s", "").equals("")) {
      return false;
    }
    return true;

  }

}
