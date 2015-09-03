package com.ciandt.techgallery.sample.service;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.techgallery.sample.persistence.dao.SampleDAOImpl;
import com.ciandt.techgallery.sample.persistence.model.Sample;
import com.ciandt.techgallery.sample.service.model.MessageResponse;
import com.ciandt.techgallery.sample.service.model.Response;
import com.ciandt.techgallery.sample.service.model.ResponseMessageEnum;
import com.ciandt.techgallery.sample.service.model.SampleResponse;
import com.googlecode.objectify.Key;

/**
 * SampleService methods implementation.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class SampleServiceImpl extends GenericService<SampleResponse, Sample, Long> implements
    SampleService {

  public SampleServiceImpl() {
    super(new SampleDAOImpl());
  }

  @Override
  public List<Response> listAll() {
    List<Sample> listSample = findAll();
    List<Response> listReturn = new ArrayList<Response>();

    for (Sample sample : listSample) {
      SampleResponse sr = new SampleResponse();
      sr.setName(sample.getName());
      listReturn.add(sr);
    }

    return listReturn;
  }

  @Override
  public Response add(SampleResponse sampleResponse) {
    // implement a transformer if you want to.
    Sample sample = new Sample();
    sample.setName(sampleResponse.getName());

    Key<Sample> key = dao.add(sample);

    MessageResponse msg = new MessageResponse();
    msg.setMsg(ResponseMessageEnum.OK.description());

    return msg;
  }

  public SampleResponse doSomeCoolThing(SampleResponse sampleResponse) {
    if (sampleResponse != null) {
      SampleResponse ret = new SampleResponse();
      ret.setName("cool");
      return ret;
    }

    return null;
  }
}
