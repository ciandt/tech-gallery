package com.ciandt.techgallery.servlets;

import static com.ciandt.techgallery.utils.ExportUtils.createCsvUsersProfile;
import static com.ciandt.techgallery.utils.ExportUtils.createXlsUsersProfile;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ciandt.techgallery.persistence.model.profile.UserProfile;
import com.ciandt.techgallery.service.impl.profile.UserProfileServiceImpl;
import com.ciandt.techgallery.service.profile.UserProfileService;

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {

  private static final Logger _LOG = Logger.getLogger(UserServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {

    String exportType = request.getParameter("type");
    response.setCharacterEncoding("UTF-8");

    if (exportType != null && exportType.equals("xls")) {
      exportXls(response);
    } else {
      exportCsv(response);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    doGet(request, response);
  }

  private void exportCsv(HttpServletResponse response) {

    UserProfileService service = UserProfileServiceImpl.getInstance();
    response.setContentType("text/csv");
    response.setHeader("Content-Disposition", "attachment; filename=\"Colaboradores.csv\"");

    try {
      List<UserProfile> allUsersProfile = service.findAllUsersProfile();
      if (allUsersProfile != null) {
        response.getOutputStream().write(createCsvUsersProfile(allUsersProfile));
      }
    } catch (Exception e) {
      _LOG.log(Level.SEVERE, "Export exception: ", e);
    }
  }

  private void exportXls(HttpServletResponse response) {

    UserProfileService service = UserProfileServiceImpl.getInstance();
    response.setContentType("application/ms-excel");
    response.setHeader("Content-Disposition", "attachment; filename=\"Colaboradores.xls\"");

    try {
      List<UserProfile> allUsersProfile = service.findAllUsersProfile();
      if (allUsersProfile != null) {
        response.getOutputStream().write(createXlsUsersProfile(allUsersProfile));
      }
    } catch (Exception e) {
      _LOG.log(Level.SEVERE, "Export exception: ", e);
    }
  }
}
