package com.ciandt.techgallery.servlets;

import static com.ciandt.techgallery.utils.ExportUtils.createCsvUsersProfile;
import static com.ciandt.techgallery.utils.ExportUtils.createXlsUsersProfile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.persistence.model.profile.UserProfile;
import com.ciandt.techgallery.service.UserServiceTG;
import com.ciandt.techgallery.service.impl.UserServiceTGImpl;
import com.ciandt.techgallery.service.impl.profile.UserProfileServiceImpl;
import com.ciandt.techgallery.service.profile.UserProfileService;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.api.server.spi.response.NotFoundException;

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {

  private static final Logger _LOG = Logger.getLogger(UserServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {

    String userName = request.getUserPrincipal().getName();
    UserServiceTG uService = UserServiceTGImpl.getInstance();

    String exportType = request.getParameter("type");
    response.setCharacterEncoding("UTF-8");

    try {
      TechGalleryUser user = uService.getUserByEmail(userName);

      if(!user.isAdmin()) {
        throw new ForbiddenException("Você precisa ser um administrador do TechGallery para esta operação.");
      }

      if (exportType != null && exportType.equals("xls")) {
        exportXls(response);
      } else {
        exportCsv(response);
      }

    } catch (Exception e) {
      _LOG.log(Level.SEVERE, "Export exception: ", e);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    doGet(request, response);
  }

  private void exportCsv(HttpServletResponse response) throws NotFoundException, IOException {

    UserProfileService service = UserProfileServiceImpl.getInstance();
    response.setContentType("text/csv");
    response.setHeader("Content-Disposition", "attachment; filename=\"Colaboradores.csv\"");

    List<UserProfile> allUsersProfile = service.findAllUsersProfile();
    if (allUsersProfile != null) {
      response.getOutputStream().write(createCsvUsersProfile(allUsersProfile));
    }
  }

  private void exportXls(HttpServletResponse response) throws NotFoundException, IOException {

    UserProfileService service = UserProfileServiceImpl.getInstance();
    response.setContentType("application/ms-excel");
    response.setHeader("Content-Disposition", "attachment; filename=\"Colaboradores.xls\"");

    List<UserProfile> allUsersProfile = service.findAllUsersProfile();
    if (allUsersProfile != null) {
      response.getOutputStream().write(createXlsUsersProfile(allUsersProfile));
    }
  }
}
