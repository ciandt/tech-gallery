package com.ciandt.techgallery.services.endpoint;

import java.util.List;

import com.ciandt.techgallery.sample.persistence.model.Sample;
import com.google.api.server.spi.config.ApiMethod;

/**
 * SampleEndpoint Interface.
 * 
 * @author felipegc
 *
 */
public interface SampleEndpoint {

  /**
   * Method that return the List of Samples.
   * 
   * @return list of samples.
   */
  public List<Sample> listSamples();

  /**
   * Method that add Sample into datastore.
   * 
   * @param sample to be persisted.
   * @return sample containing datas to be shown.
   */
  public Sample addSample(Sample sample);

}
