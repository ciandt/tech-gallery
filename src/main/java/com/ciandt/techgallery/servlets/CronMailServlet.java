package com.ciandt.techgallery.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CronMailServlet extends HttpServlet {

  public static final Logger _LOG = Logger.getLogger(CronMailServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      resp.getWriter().println("Teste");
      _LOG.info("Sending out email");
    } catch (Exception ex) {
    }
  }
}
