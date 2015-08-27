package com.ciandt.techgallery.sample.service.endpoint;

import java.util.List;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.sample.service.SampleService;
import com.ciandt.techgallery.sample.service.SampleServiceImpl;
import com.ciandt.techgallery.sample.service.model.Response;
import com.ciandt.techgallery.sample.service.model.SampleResponse;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

/**
 * SampleEndpoint Implementation.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
@Api(name = "sampleEndpointImpl", version = "v1", scopes = {Constants.EMAIL_SCOPE}, clientIds = {
    Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE})
public class SampleEndpointImpl implements SampleEndpoint {

  SampleService service = new SampleServiceImpl();

  /**
   * @inheritDoc
   */
  @Override
  public List<Response> listAll() {
    return service.listAll();
  }

  /**
   * @inheritDoc
   */
  @ApiMethod(name = "sample.add", httpMethod = "post")
  @Override
  public Response addSample(SampleResponse response) {
    return service.add(response);
  }

}
