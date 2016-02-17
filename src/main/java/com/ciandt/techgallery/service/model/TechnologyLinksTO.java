package com.ciandt.techgallery.service.model;

import com.ciandt.techgallery.persistence.model.TechnologyLink;

import java.util.List;

/**
 * TechnologyLinksTO.
 *
 * @author Sidharta Noleto
 *
 */
public class TechnologyLinksTO implements Response {

  List<TechnologyLink> links;

  public List<TechnologyLink> getLinks() {
    return links;
  }

  public void setLinks(List<TechnologyLink> links) {
    this.links = links;
  }

}
