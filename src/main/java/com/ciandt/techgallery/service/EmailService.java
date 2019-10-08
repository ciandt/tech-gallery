package com.ciandt.techgallery.service;

import com.ciandt.techgallery.service.email.EmailConfig;



public interface EmailService {

  void push(EmailConfig email);

  void execute(Long emailNotificationId);

}
