package com.ciandt.techgallery.services.endpoint;

import java.util.List;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.sample.persistence.model.Sample;
import com.ciant.techgallery.sample.services.SampleService;
import com.ciant.techgallery.sample.services.SampleServiceImpl;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.googlecode.objectify.Key;

/**
 * SampleEndpoint Implementation.
 * 
 * @author felipegc
 *
 */
@Api(name = "sEndpointImpl", version = "v1", scopes = {Constants.EMAIL_SCOPE}, clientIds = {
    Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE})
public class SEndpointImpl implements SampleEndpoint {

  SampleService service = new SampleServiceImpl();

  /**
   * @inheritDoc
   */
  @Override
  public List<Sample> listSamples() {
    return service.findAll();
  }

  /**
   * @inheritDoc
   */
  @ApiMethod(name = "sample.add", httpMethod = "post")
  @Override
  public Sample addSample(Sample sample) {
    Key<Sample> key = service.add(sample);

    // implement a transformer if you want to.
    Sample sampleRetorno = new Sample();
    sampleRetorno.setId(key.getId());
    sampleRetorno.setName(key.getName());

    return sampleRetorno;
  }

}
