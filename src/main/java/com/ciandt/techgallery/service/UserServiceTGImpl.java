package com.ciandt.techgallery.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ciandt.techgallery.persistence.dao.UserDAO;
import com.ciandt.techgallery.persistence.dao.UserDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.UserResponse;
import com.ciandt.techgallery.service.model.UsersResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;

public class UserServiceTGImpl implements UserServiceTG {

  UserDAO userDAO = new UserDAOImpl();
  static final String PEOPLE_ENDPOINT = "https://people.cit.com.br/profile/";

  /**
   * GET for getting all users.
   */
  @Override
  public Response getUsers() throws NotFoundException {
    List<TechGalleryUser> userEntities = userDAO.findAll();
    // if user list is null, return a not found exception
    if (userEntities == null) {
      throw new NotFoundException("No user was found.");
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
    // if user is null, return a bad request exception
    if (user == null) {
      throw new BadRequestException("User JSON cannot be null.");
    }
    // user obligatory information
    String userName = user.getName();
    String userEmail = user.getEmail();
    // user's name cannot be blank
    if (userName == null || userName.equals("")) {
      throw new BadRequestException("User's name cannot be blank.");
    } else {
      // user's email cannot be blank
      if (userEmail == null || userEmail.equals("")) {
        throw new BadRequestException("User's email cannot be blank.");
      } else {

        TechGalleryUser userEntity = new TechGalleryUser();
        userEntity.setName(userName);
        userEntity.setEmail(userEmail);
        userEntity.setPhoto(userEntity.getPhoto());
        userDAO.add(userEntity);
        // set the id and return it
        user.setId(userEntity.getId());
        return user;
      }
    }

  }

  /**
   * PUT for editing a user.
   */
  @Override
  public Response updateUser(final UserResponse user) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * GET for getting an user by its login.
   */
  @Override
  public Response getUserByLogin(final String login) throws NotFoundException {
    TechGalleryUser userEntity = userDAO.findByLogin(login);
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
   * Uses a user login to get
   * 
   * @param userlogin
   */
  @Override
  public Response getUserFromProvider(final String user) throws NotFoundException{
    String fullRequest = PEOPLE_ENDPOINT + user + "?format=json";
    try {
      URL url = new URL(fullRequest);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoOutput(true);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Authorization", "Basic <USER:PASSWORD all base64 encoded>");
      ObjectMapper mapper = new ObjectMapper(); 
      Map<String,Object> userData = mapper.readValue(conn.getInputStream(), Map.class); 
      UserResponse uResp = new UserResponse();
      uResp.setEmail((String) userData.get("email"));
      uResp.setName((String) userData.get("name"));
      //uResp.set(null);
    
    } catch (MalformedURLException e) {
      //TODO ...
    } catch (IOException e) {
      //TODO ...
    }
    UserResponse response = new UserResponse();
    return response;
  }

}
