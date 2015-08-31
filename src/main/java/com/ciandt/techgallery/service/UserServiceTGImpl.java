package com.ciandt.techgallery.service;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.techgallery.persistence.dao.UserDAO;
import com.ciandt.techgallery.persistence.dao.UserDAOImpl;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.UserResponse;
import com.ciandt.techgallery.service.model.UsersResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserServiceTGImpl implements UserServiceTG {

  UserDAO userDAO = new UserDAOImpl();

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
    String userEmail = user.getName();
    // user's name cannot be blank
    if (userName == null || userName.equals("")) {
      throw new BadRequestException("User's name cannot be blank.");
    } else {
      // user's email cannot be blank
      if (userEmail == null || userEmail.equals("")) {
        throw new BadRequestException("User's email cannot be blank.");
      } else {
        // carregar perfil de acordo com seu email
        // UserService userService = UserServiceFactory.getUserService();
        // User googleUser = userService.getCurrentUser();
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

}
