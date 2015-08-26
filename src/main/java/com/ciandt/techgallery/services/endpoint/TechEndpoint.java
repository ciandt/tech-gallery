package com.ciandt.techgallery.services.endpoint;

import com.ciant.techgallery.sample.service.model.Response;

public interface TechEndpoint {

  /**
   * Method that add a Technology into datastore.
   * 
   * @param technology to be persisted.
   * @return technology containing datas to be shown.
   */
  public Response addTechnology(Response technologyResponse);
}
