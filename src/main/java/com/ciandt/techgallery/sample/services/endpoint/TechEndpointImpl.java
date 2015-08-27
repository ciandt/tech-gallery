package com.ciandt.techgallery.sample.services.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciant.techgallery.sample.service.model.Response;
import com.ciant.techgallery.sample.service.model.TechResponse;
import com.ciant.techgallery.sample.services.TechServiceImpl;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

@Api(name = "techEndpointImpl", version = "v1", scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE})
public class TechEndpointImpl {

  
  TechServiceImpl service = new TechServiceImpl();

  @ApiMethod(name = "tech.add", httpMethod = "post")
  public Response addTechnology(TechResponse pojo) {
    service.add(pojo);
    return pojo;
  }

}
