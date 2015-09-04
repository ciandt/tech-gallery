package com.ciandt.techgallery.sample.service.model;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.techgallery.persistence.model.TechGalleryUser;

/**
 * TechnologyResponse entity.
 * 
 * @author Daniel Eduardo 
 *
 */
public class EndorsementsGroupedByEndorsedTransient extends Response {

  TechGalleryUser endorsed;
  
  List<TechGalleryUser> endorsers;
  
}
