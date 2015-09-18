package com.ciandt.techgallery.sample.service.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ciandt.techgallery.sample.persistence.dao.UserGroupDAO;
import com.ciandt.techgallery.sample.persistence.dao.UserGroupDAOImpl;
import com.ciandt.techgallery.sample.persistence.model.UserGroup;

/**
 * Http Servlet class for testing Objectify.
 * 
 * @author felipers
 *
 */
@SuppressWarnings("serial")
public class DAOExample extends HttpServlet {

  @SuppressWarnings("unused")
  private static final Logger log = Logger.getLogger(DAOExample.class.getName());

  /**
   * GET request for getting groups.
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    UserGroupDAO ugDAO = new UserGroupDAOImpl();

    List<UserGroup> allGroups = ugDAO.findAll();
    for (int i = 0; i < allGroups.size(); i++) {
      log.info("Group " + allGroups.get(i).getName() + " id: " + allGroups.get(i).getId());
    }
    // get one group by name
    UserGroup group = ugDAO.findByName("Group 7");
    log.info("Group: " + group.getName() + " id: " + group.getId());
  }

  /**
   * PUT request for updating group.
   */
  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    UserGroupDAO ugDAO = new UserGroupDAOImpl();

    log.info("Get by id: 5770237022568448");
    UserGroup idTest = ugDAO.findById(5770237022568448L);
    log.info("Group " + idTest.getName() + " id: " + idTest.getId());
    idTest.setName("8UP");
    ugDAO.updateGroup(idTest);
    log.info("Group " + idTest.getName() + " id: " + idTest.getId());
  }

  /**
   * DELETE request for deleting group.
   */
  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    UserGroupDAO ugDAO = new UserGroupDAOImpl();
    UserGroup idTest = ugDAO.findById(5770237022568448L);
    log.info("Trying to delete id: 5770237022568448");
    ugDAO.deleteGroup(idTest);
    log.info("Deleted id: 5770237022568448");
  }

  /**
   * POST request for saving groups.
   */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    UserGroupDAO ugDAO = new UserGroupDAOImpl();

    for (int i = 0; i < 10; i++) {
      UserGroup group = new UserGroup();
      group.setName("Group " + i);
      log.info("Trying to save group " + i + " id: " + group.getId());
      ugDAO.addGroup(group);
      log.info("Saved group " + i + " id: " + group.getId());
    }
  }
}
