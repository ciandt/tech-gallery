package com.ciant.techgallery.sample.services;

import com.ciandt.techgallery.sample.persistence.dao.SampleDAOImpl;
import com.ciandt.techgallery.sample.persistence.model.Sample;
import com.ciant.techgallery.sample.service.model.SampleResponse;

/**
 * SampleService methods implementation.
 * 
 * @author felipegc
 *
 */
public class SampleServiceImpl extends GenericService<SampleResponse, Sample, Long> implements
    SampleService {

  public SampleServiceImpl() {
    super(new SampleDAOImpl());
  }

  @Override
  public SampleResponse add(SampleResponse pojo) {
    // TODO Auto-generated method stub
    return null;
  }

}
