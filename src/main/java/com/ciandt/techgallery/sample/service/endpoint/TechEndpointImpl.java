package com.ciandt.techgallery.sample.service.endpoint;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.sample.service.TechServiceImpl;
import com.ciandt.techgallery.sample.service.model.Response;
import com.ciandt.techgallery.sample.service.model.TechResponse;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

@Api(name = "techEndpointImpl", version = "v1", scopes = {Constants.EMAIL_SCOPE}, clientIds = {
    Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE})
public class TechEndpointImpl implements TechEndpoint {


  TechServiceImpl service = new TechServiceImpl();

  @ApiMethod(name = "tech.add", httpMethod = "post")
  @Override
  public Response addTechnology(TechResponse technologyResponse) {
    service.add(technologyResponse);
    return technologyResponse;
  }

}
