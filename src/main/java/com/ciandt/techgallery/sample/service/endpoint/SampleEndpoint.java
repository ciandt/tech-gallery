package com.ciandt.techgallery.sample.service.endpoint;

import java.util.List;

import com.ciandt.techgallery.sample.service.model.Response;
import com.ciandt.techgallery.sample.service.model.SampleResponse;

/**
 * SampleEndpoint Interface.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public interface SampleEndpoint {

  /**
   * Method that return the List of Samples.
   * 
   * @return list of samples.
   */
  public List<Response> listAll();

  /**
   * Method that add Sample into datastore.
   * 
   * @param sample to be persisted.
   * @return sample containing datas to be shown.
   */
  public Response addSample(SampleResponse sampleResponse);

}
