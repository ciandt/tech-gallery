package com.ciandt.techgallery.services.endpoint;

import java.util.List;

import com.ciandt.techgallery.Constants;
import com.ciandt.techgallery.sample.persistence.model.Sample;
import com.ciant.techgallery.sample.services.SampleService;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.googlecode.objectify.Key;

@Api(name = "sampleEndpointImpl", version = "v1", scopes = {Constants.EMAIL_SCOPE}, clientIds = {
    Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE})
public class SampleEndpointImpl implements SampleEndpoint {

  SampleService service = new SampleService();

  public List<Sample> listSamples() {
    List<Sample> samples = service.findAll();
    return samples;
  }

  @ApiMethod(name = "sample.add", httpMethod = "post")
  public Sample addSample(Sample sample) {
    Key<Sample> key = service.add(sample);

    // implement a transformer if you want to.
    Sample sampleRetorno = new Sample();
    sampleRetorno.setId(key.getId());
    sampleRetorno.setName(key.getName());

    return sampleRetorno;
  }
}
