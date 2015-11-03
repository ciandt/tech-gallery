package com.ciandt.techgallery.servlets;


import com.ciandt.techgallery.service.EmailService;
import com.ciandt.techgallery.service.impl.EmailServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EmailServlet extends HttpServlet {
  private EmailService emailService = EmailServiceImpl.getInstance();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    emailService.execute(request.getParameter("subject"), request.getParameter("body"),
        request.getParameter("reason"), request.getParameter("to"));
  }
}
