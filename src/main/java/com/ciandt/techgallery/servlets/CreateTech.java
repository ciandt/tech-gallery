package com.ciandt.techgallery.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateTech extends HttpServlet {

  private static final long serialVersionUID = -6323934778871180015L;

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String urlPage = "/createTech.html";
    if (!req.getQueryString().isEmpty()) {
      urlPage += "?" + req.getQueryString();
    }
    resp.setContentType("text/html");
    resp.sendRedirect(urlPage);
  }
}
