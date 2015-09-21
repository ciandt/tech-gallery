package com.ciandt.techgallery.sample.service.endpoint;

import java.util.List;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.sample.persistence.dao.UserGroupDAO;
import com.ciandt.techgallery.sample.persistence.dao.UserGroupDAOImpl;
import com.ciandt.techgallery.sample.persistence.model.UserGroup;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

/**
 * UserGroupEndpoint Implementation class.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Api(name = "userGroupEndpointImpl", version = "v1", scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE})
public class UserGroupEndpointImpl implements UserGroupEndpoint {

  public List<UserGroup> listUsers() {
    UserGroupDAO ugDAO = new UserGroupDAOImpl();
    List<UserGroup> allGroups = ugDAO.findAll();
    return allGroups;
  }

  @ApiMethod(name = "userGroup.inserts", httpMethod = "post")
  public UserGroup insertUserGroup(UserGroup userGroup) {
    UserGroupDAO ugDAO = new UserGroupDAOImpl();

    UserGroup response = new UserGroup();
    ugDAO.addGroup(userGroup);

    StringBuilder responseBuilder = new StringBuilder();
    responseBuilder.append(userGroup.getName());
    response.setName(responseBuilder.toString());
    return response;
  }

}
