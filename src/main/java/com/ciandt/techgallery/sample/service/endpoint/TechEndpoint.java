package com.ciandt.techgallery.sample.service.endpoint;

import com.ciandt.techgallery.sample.service.model.Response;
import com.ciandt.techgallery.sample.service.model.TechResponse;

public interface TechEndpoint {

  /**
   * Method that add a Technology into datastore.
   * 
   * @param technology to be persisted.
   * @return technology containing datas to be shown.
   */
  public Response addTechnology(TechResponse technologyResponse);
}
