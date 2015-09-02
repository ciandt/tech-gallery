package com.ciandt.techgallery.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.UserResponse;
import com.ciandt.techgallery.service.model.UsersResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.repackaged.org.codehaus.jackson.JsonParseException;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;

public class UserServiceTGImpl implements UserServiceTG {

  TechGalleryUserDAO userDAO = new TechGalleryUserDAOImpl();
  static final String PEOPLE_ENDPOINT = "https://people.cit.com.br/profile/";
  static final String USER_NOT_FOUND = "User not found";
  static final String PROVIDER_AUTH = "Basic ZWR1YXJkb2dmOk04YzBkOGs4Kw==";

  /**
   * GET for getting all users.
   */
  @Override
  public Response getUsers() throws NotFoundException {
    List<TechGalleryUser> userEntities = userDAO.findAll();
    // if user list is null, return a not found exception
    if (userEntities == null) {
      throw new NotFoundException(USER_NOT_FOUND);
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
      throw new NotFoundException("No user was found.");
    } else {
      UserResponse response = new UserResponse();
      response.setId(userEntity.getId());
      response.setName(userEntity.getName());
      response.setEmail(userEntity.getEmail());
      response.setPhoto(userEntity.getPhoto());
      return response;
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
      throw new BadRequestException("User's email cannot be blank.");
    } else {
      TechGalleryUser userEntity = new TechGalleryUser();
      fillUserData(user, userEntity);
      userDAO.add(userEntity);
      // set the id and return it
      user.setId(userEntity.getId());
      return user;
    }
  }

  /**
   * PUT for editing a user.
   * 
   * @throws BadRequestException
   */
  @Override
  public Response updateUser(final UserResponse user) throws BadRequestException {
    if (!userDataIsValid(user) && user.getId() != null) {
      throw new BadRequestException("User's email cannot be blank.");
    } else {
      TechGalleryUser tgCurrentUser = userDAO.findById(user.getId());
      fillUserData(user, tgCurrentUser);
      userDAO.update(tgCurrentUser);
      return user;
    }
  }

  private void fillUserData(final UserResponse user, TechGalleryUser tgCurrentUser) {
    String userName = user.getName();
    String userEmail = user.getEmail();
    String userPhoto = user.getPhoto();
    tgCurrentUser.setName(userName);
    tgCurrentUser.setEmail(userEmail);
    if (userPhoto != null) {
      tgCurrentUser.setPhoto(userPhoto);
    }
  }

  /**
   * GET for getting an user by its login.
   */
  @Override
  public Response getUserByLogin(final String login) throws NotFoundException {
    TechGalleryUser userEntity = userDAO.findByLogin(login);
    if (userEntity == null) {
      throw new NotFoundException(USER_NOT_FOUND);
    } else {
      UserResponse response = new UserResponse();
      response.setId(userEntity.getId());
      response.setName(userEntity.getName());
      response.setEmail(userEntity.getEmail());
      response.setPhoto(userEntity.getPhoto());
      return response;
    }
  }

  @Override
  public TechGalleryUser getUserByNameAndEmail(final String name, final String email)
      throws NotFoundException, BadRequestException, InternalServerErrorException {
    return userDAO.findByNameAndEmail(name, email);
  }

  /**
   *  
   * Checks if user exists on provider, syncs with tech gallery's datastore. If user exists,
   * adds to TG's datastore (if not there). Returns the user.
   * 
   * @param userLogin
   * @return
   * @throws NotFoundException
   * @throws BadRequestException
   * @throws InternalServerErrorException
   */
  @Override
  public TechGalleryUser getUserSyncedWithProvider(final String userLogin) throws NotFoundException,
      BadRequestException, InternalServerErrorException {
    TechGalleryUser tgUser = null;
    try {
      UserResponse uResp = (UserResponse) getUserFromProvider(userLogin);
      tgUser = getUserByNameAndEmail(uResp.getName(), uResp.getEmail());
      if (tgUser == null) {
        addUser(uResp);
      }
    } catch (BadRequestException e) {
      //User not found on provider
      if (e.getMessage().equals(USER_NOT_FOUND)) {
        //Logs to App Engine
        System.err.println(USER_NOT_FOUND+": "+e.getMessage());
        //Might delete user from tech gallery datastore in the future
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
  public Response getUserFromProvider(final String userLogin) throws NotFoundException,
      BadRequestException, InternalServerErrorException {

    String fullRequest = PEOPLE_ENDPOINT + userLogin + "?format=json";
    UserResponse uResp = new UserResponse();
    try {
      URL url = new URL(fullRequest);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Authorization", PROVIDER_AUTH);
      ObjectMapper mapper = new ObjectMapper();

      Map<String, Object> providerResponse = mapper.readValue(conn.getInputStream(), Map.class);
      HashMap<String, Object> userData = (LinkedHashMap) providerResponse.get("personal_info");
      uResp.setEmail((String) userData.get("email"));
      uResp.setName((String) userData.get("name"));

    } catch (JsonParseException e) {
      throw new BadRequestException(USER_NOT_FOUND);

    } catch (MalformedURLException e) {
      throw new BadRequestException(e.getMessage());

    } catch (IOException e) {
      System.err.println("An internal server error ocurred!");
      System.err.println(e.getMessage());
      throw new InternalServerErrorException("An internal server error ocurred.");

    }
    return uResp;
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

    // user obligatory information
    String userName = user.getName();
    String userEmail = user.getEmail();
    // user's name cannot be blank
    if (userName == null || userName.replaceAll("\\s", "").equals("")) {
      return false;
    }
    // user's email cannot be blank
    if (userEmail == null || userEmail.replaceAll("\\s", "").equals("")) {
      return false;
    }
    return true;

  }

}
