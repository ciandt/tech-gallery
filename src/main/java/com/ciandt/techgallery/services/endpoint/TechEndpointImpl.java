package com.ciandt.techgallery.services.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciant.techgallery.sample.service.model.Response;
import com.ciant.techgallery.sample.service.model.TechnologyResponse;
import com.ciant.techgallery.sample.services.TechnologyServiceImpl;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

@Api(name = "techEndpointImpl", version = "v1", scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE})
public class TechEndpointImpl {

  
  TechnologyServiceImpl service = new TechnologyServiceImpl();

//  public List<TechnologyPojo> listTechnologies() {
//    List<TechnologyPojo> list = service.findAll();
//    return list;
//  }

  @ApiMethod(name = "tech.add", httpMethod = "post")
  public Response addTechnology(TechnologyResponse pojo) {
    service.add(pojo);
    return pojo;
  }

}
