package com.ciandt.techgallery.servlets;

import com.ciandt.techgallery.service.EmailService;
import com.ciandt.techgallery.service.impl.EmailServiceImpl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CronMailServlet extends HttpServlet {

  public static final Logger _LOG = Logger.getLogger(CronMailServlet.class.getName());
  private EmailService emailService = EmailServiceImpl.getInstance();

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    emailService.sendEmailtoFollowers();
    _LOG.info("Executing schedule task.");
  }
}
