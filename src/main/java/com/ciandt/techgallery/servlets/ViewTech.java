package com.ciandt.techgallery.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ViewTech extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String urlPage = "/viewTech.html";
    if (!req.getQueryString().isEmpty()) {
      urlPage += "?" + req.getQueryString();
    }
    resp.setContentType("text/html");
    resp.sendRedirect(urlPage);
  }
}
