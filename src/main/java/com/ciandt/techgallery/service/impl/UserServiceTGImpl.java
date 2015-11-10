package com.ciandt.techgallery.service.impl;

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
import java.util.Scanner;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.impl.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.enums.ValidationMessageEnums;
import com.ciandt.techgallery.service.impl.profile.UserProfileServiceImpl;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.UserResponse;
import com.ciandt.techgallery.service.model.UsersResponse;
import com.ciandt.techgallery.utils.i18n.I18n;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

public class UserServiceTGImpl implements UserServiceTG {

  /*
   * Constants --------------------------------------------
   */
  private static final String PEOPLE_ENDPOINT_PROFILE = "https://people.cit.com.br/profile/";
  private static final String PEOPLE_ENDPOINT_SEARCH = "https://people.cit.com.br/search/json/?q=";
  private static final String EMAIL_DOMAIN = "@ciandt.com";
  private static final int INDEX_PEOPLE_API_NAME = 0;
  private static final int INDEX_PEOPLE_API_LOGIN = 1;

  private static final String OPERATION_FAILED = "Operation failed";

  private static final Logger log = Logger.getLogger(UserServiceTGImpl.class.getName());
  private static final I18n i18n = I18n.getInstance();

  /*
   * Attributes --------------------------------------------
   */
  private static UserServiceTGImpl instance;
  private MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
  private final Expiration memCacheTimeExpliration = Expiration.byDeltaSeconds(7200);

  TechGalleryUserDAO userDao = TechGalleryUserDAOImpl.getInstance();

  /*
   * Constructors --------------------------------------------
   */
  private UserServiceTGImpl() {
  }

  /**
   * For singleton
   *
   * @return the current instance, if it exists. The recently created instance,
   *         if not.
   */
  /**
   * Singleton method for the service.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros
   *         Moreira </a>
   * @since 08/10/2015
   *
   * @return UserServiceTGImpl instance.
   */
  public static UserServiceTGImpl getInstance() {
    if (instance == null) {
      instance = new UserServiceTGImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  /**
   * Gets all users from the datastore.
   */
  @Override
  public Response getUsers() throws NotFoundException {
    List<TechGalleryUser> userEntities = userDao.findAll();
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
   * Gets a TechGalleryUser by id.
   *
   * @param id
   *          the user's id
   * @throws NotFoundException
   *           if the user is not found
   */
  @Override
  public TechGalleryUser getUser(final Long id) throws NotFoundException {
    TechGalleryUser userEntity = userDao.findById(id);
    // if user is null, return a not found exception
    if (userEntity == null) {
      throw new NotFoundException(i18n.t("No user was found."));
    } else {
      return userEntity;
    }
  }

  /**
   * Adds a new user to Tech Gallery.
   *
   * @param user
   *          the TechGallery user to be added
   * @return added user
   * @throws BadRequestException
   *           when the user email parameter is missing
   */
  @Override
  public TechGalleryUser addUser(final TechGalleryUser user) throws BadRequestException {
    if (!userDataIsValid(user)) {
      throw new BadRequestException(i18n.t("User's email cannot be blank."));
    } else {
      userDao.add(user);
      UserProfileServiceImpl.getInstance().createProfile(user);
      return user;
    }
  }

  /**
   * This method should be executed whenever a user logs in It check whether the
   * user exists on TG's datastore and create them, if not. It also checks if
   * the user's email has been changed and update it, in case it was changed.
   *
   * @param user
   *          A Google AppEngine API user
   * @return A response with the user data as it is on TG datastore
   *
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   * @throws OAuthRequestException
   *           in case of authentication problem
   * @throws IOException
   *           in case of a IO exception
   */
  @Override
  public TechGalleryUser handleLogin(Integer timezoneOffset, final User user, HttpServletRequest req)
      throws NotFoundException, BadRequestException, InternalServerErrorException, IOException,
      OAuthRequestException {
    if (user == null) {
      throw new OAuthRequestException(i18n.t("Authorization error"));
    }
    String userEmail = user.getEmail();
    String header = req.getHeader("Authorization");
    String accesstoken = header.substring(header.indexOf(' ')).trim(); // "Bearer
                                                                       // ".length

    GoogleCredential credential = new GoogleCredential().setAccessToken(accesstoken);
    Plus plus = new Plus.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
        .setApplicationName(i18n.t("Tech Gallery")).build();
    Person person = plus.people().get("me").execute();
    TechGalleryUser tgUser = userDao.findByGoogleId(user.getUserId());
    // Couldn't find by googleID. Try email
    if (tgUser == null) {
      tgUser = userDao.findByEmail(userEmail);
    }
    // Ok, we couldn't find it. Create it.
    if (tgUser == null) {
      tgUser = new TechGalleryUser();
    }
    updateUserInformation(user, person, tgUser);
    tgUser.setTimezoneOffset(timezoneOffset);
    addUser(tgUser);
    log.info("User " + tgUser.getName() + " added/updated");
    return tgUser;
  }

  /**
   * Updates current Tech Gallery user information with user data found on
   * Google.
   *
   * @param user
   *          Google user
   * @param person
   *          Google Plus person information
   * @param tgUser
   *          Tech Gallery user
   */
  private void updateUserInformation(final User user, Person person, TechGalleryUser tgUser) {
    String plusEmail = user.getEmail();
    String plusPhoto = person.getImage().getUrl();
    String plusName = person.getDisplayName();

    String currentEmail = tgUser.getEmail();
    String currentPhoto = tgUser.getPhoto();
    String currentName = tgUser.getName();

    if (currentEmail == null || !currentEmail.equals(plusEmail)) {
      tgUser.setEmail(plusEmail);
    }
    if (currentPhoto == null || !currentPhoto.equals(plusPhoto)) {
      tgUser.setPhoto(plusPhoto);
    }
    if (currentName == null || !currentName.equals(plusName)) {
      tgUser.setName(plusName);
    }
    if (tgUser.getGoogleId() == null) {
      tgUser.setGoogleId(user.getUserId());
    }
  }

  /**
   * Updates a user, with validation.
   *
   * @throws BadRequestException
   *           in case of a missing parameter
   * @return the updated user
   */
  @Override
  public TechGalleryUser updateUser(final TechGalleryUser user) throws BadRequestException {
    if (!userDataIsValid(user) && user.getId() != null) {
      throw new BadRequestException(i18n.t("User's email cannot be blank."));
    } else {
      userDao.update(user);
      return user;
    }
  }

  /**
   * GET for getting an user by its login.
   *
   * @param login
   *          the user's login
   * @return the user found
   */
  @Override
  public TechGalleryUser getUserByLogin(final String login) throws NotFoundException {
    TechGalleryUser userEntity = userDao.findByLogin(login);
    if (userEntity == null) {
      throw new NotFoundException(OPERATION_FAILED);
    } else {
      return userEntity;
    }
  }

  /**
   * Finds a TechGalleryUser by his/her email.
   *
   * @param email
   *          the user's email
   * @throws NotFoundException
   *           if the user is not found
   */
  @Override
  public TechGalleryUser getUserByEmail(final String email) throws NotFoundException {
    TechGalleryUser tgUser = userDao.findByEmail(email);
    if (tgUser == null) {
      throw new NotFoundException(ValidationMessageEnums.USER_NOT_EXIST.message());
    } else {
      return tgUser;
    }
  }

  /**
   * Checks if user exists on provider, syncs with tech gallery's datastore. If
   * user exists, adds to TG's datastore (if not there). Returns the user.
   *
   * @param userLogin
   *          userLogin
   * @return the user saved on the datastore
   * @throws InternalServerErrorException
   *           in case something goes wrong
   * @throws NotFoundException
   *           in case the information are not founded
   * @throws BadRequestException
   *           in case a request with problem were made.
   * @return user sinchronized in provider.
   */
  @Override
  public TechGalleryUser getUserSyncedWithProvider(final String userLogin)
      throws NotFoundException, InternalServerErrorException, BadRequestException {
    TechGalleryUser tgUser = null;
    TechGalleryUser userResp = getUserFromProvider(userLogin);
    tgUser = userDao.findByEmail(getUserFromProvider(userLogin).getEmail());
    if (tgUser == null) {
      tgUser = new TechGalleryUser();
      tgUser.setEmail(userResp.getEmail());
      tgUser.setName(userResp.getName());
      tgUser = addUser(tgUser);
      // Key<TechGalleryUser> key = userDao.add(tgUser);
      // tgUser.setId(key.getId());
    }
    return tgUser;
  }

  /**
   * GET Calls the provider API passing a login to obtain user information.
   *
   * @param userlogin
   *          the user login to pass to the provider API
   * @throws NotFoundException
   *           in case the user is not found on provider
   * @throws BadRequestException
   *           in case of JSON or URL error
   * @throws InternalServerErrorException
   *           if any IO exceptions occur
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public TechGalleryUser getUserFromProvider(final String userLogin)
      throws NotFoundException, BadRequestException, InternalServerErrorException {

    TechGalleryUser tgUser = new TechGalleryUser();
    Map<String, Object> providerResponse = peopleApiConnect(userLogin, PEOPLE_ENDPOINT_PROFILE);
    HashMap<String, Object> userData = (LinkedHashMap) providerResponse.get("personal_info");
    tgUser.setEmail((String) userData.get("email"));
    tgUser.setName((String) userData.get("name"));
    return tgUser;
  }

  /**
   * GET Calls the provider API passing a login to obtain a list of users
   * information.
   *
   * @param string
   *          to search on provider by name or login
   *
   * @throws NotFoundException
   *           in case the user is not found on provider
   * @throws BadRequestException
   *           in case of JSON or URL error
   * @throws InternalServerErrorException
   *           if any IO exceptions occur
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<UserResponse> getUsersByPartialLogin(String userLogin)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    String userLoginFormatted = userLogin + "*";
    List<UserResponse> techUsers = (List<UserResponse>) syncCache.get(userLoginFormatted);
    if (techUsers == null) {
      techUsers = new ArrayList<>();
      Map<String, Object> map = peopleApiConnect(userLoginFormatted, PEOPLE_ENDPOINT_SEARCH);
      ArrayList<?> peopleApiResponse = (ArrayList<?>) map.get("data");
      for (int index = 0; index < peopleApiResponse.size(); index++) {
        ArrayList<?> peopleApiUser = (ArrayList<?>) peopleApiResponse.get(index);
        final String peopleNameLowerCase = peopleApiUser.get(INDEX_PEOPLE_API_NAME).toString().toLowerCase();
        final String peopleLoginLowerCase = peopleApiUser.get(INDEX_PEOPLE_API_LOGIN).toString().toLowerCase();
        if (peopleNameLowerCase.contains(userLogin.toLowerCase())
            || peopleLoginLowerCase.contains(userLogin.toLowerCase())) {

          TechGalleryUser foundUser = userDao
              .findByEmail((String) peopleApiUser.get(INDEX_PEOPLE_API_LOGIN) + EMAIL_DOMAIN);
          UserResponse tgUser = new UserResponse();
          if (foundUser != null) {
            tgUser.setEmail(foundUser.getEmail().split("@")[0]);
            tgUser.setName(foundUser.getName());
            tgUser.setPhoto(foundUser.getPhoto());
          } else {
            tgUser.setEmail((String) peopleApiUser.get(INDEX_PEOPLE_API_LOGIN));
            tgUser.setName((String) peopleApiUser.get(INDEX_PEOPLE_API_NAME));
            tgUser.setPhoto(null);
          }
          techUsers.add(tgUser);
        }
      }
      syncCache.put(userLoginFormatted, techUsers, memCacheTimeExpliration);
    }
    return techUsers;
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> peopleApiConnect(final String userLogin, final String urlEndPoint)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    String fullRequest = urlEndPoint + userLogin;
    if (urlEndPoint.equals(PEOPLE_ENDPOINT_PROFILE)) {
      fullRequest += "?format=json";
    }
    try {
      InputStream resourceStream = UserServiceTGImpl.class.getClassLoader()
          .getResourceAsStream("people_basic_auth.txt");

      String auth = convertStreamToString(resourceStream);

      URL url = new URL(fullRequest);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Authorization", auth);
      ObjectMapper mapper = new ObjectMapper();

      if (conn.getResponseCode() == 200) {
        Map<String, Object> providerResponse = mapper.readValue(conn.getInputStream(), Map.class);
        return providerResponse;
      } else {
        throw new NotFoundException(i18n.t("User not found"));
      }

    } catch (JsonParseException e) {
      throw new BadRequestException(OPERATION_FAILED);

    } catch (MalformedURLException e) {
      throw new BadRequestException(e.getMessage());

    } catch (IOException e) {
      log.info("An internal server error ocurred!");
      log.info(e.getMessage());
      throw new InternalServerErrorException(i18n.t("An internal server error ocurred."));
    }
  }

  @SuppressWarnings("resource")
  private static String convertStreamToString(InputStream is) {
    Scanner scanner = new Scanner(is).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }

  /**
   * Validates user data.
   *
   * @param user
   *          the TechGalleryUser entity
   * @return true if data is valid, false otherwise
   */
  private static boolean userDataIsValid(TechGalleryUser user) {
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

  @Override
  public TechGalleryUser getUserByGoogleId(String googleId) {
    return userDao.findByGoogleId(googleId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ciandt.techgallery.service.UserServiceTG#saveUserPreference(java.lang.
   * Boolean, com.google.appengine.api.users.User)
   */
  @Override
  public TechGalleryUser saveUserPreference(Boolean postGooglePlusPreference, User user)
      throws NotFoundException, BadRequestException, InternalServerErrorException, IOException, OAuthRequestException {

    validateUser(user);

    TechGalleryUser techUser = userDao.findByEmail(user.getEmail());
    if (postGooglePlusPreference != null) {
      techUser.setPostGooglePlusPreference(postGooglePlusPreference);
      userDao.update(techUser);
    }
    return techUser;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ciandt.techgallery.service.UserServiceTG#validateUser(com.google.
   * appengine.api.users.User)
   */
  @Override
  public TechGalleryUser validateUser(User user)
      throws BadRequestException, NotFoundException, InternalServerErrorException {

    if (user == null || user.getUserId() == null || user.getUserId().isEmpty()) {
      throw new BadRequestException(ValidationMessageEnums.USER_GOOGLE_ENDPOINT_NULL.message());
    }

    final TechGalleryUser techUser = getUserByGoogleId(user.getUserId());
    if (techUser == null) {
      throw new NotFoundException(ValidationMessageEnums.USER_NOT_EXIST.message());
    }
    return techUser;
  }

}
