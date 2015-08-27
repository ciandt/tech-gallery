package com.ciandt.techgallery.sample.service;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.techgallery.sample.persistence.dao.SampleDAOImpl;
import com.ciandt.techgallery.sample.persistence.model.Sample;
import com.ciandt.techgallery.sample.service.model.MensagemResponse;
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
public class SampleServiceImpl extends GenericService<SampleResponse, Sample, Long> implements SampleService {

  public SampleServiceImpl() {
    super(new SampleDAOImpl());
  }

  @Override
  public List<Response> listAll() {
    List<Sample> listSample = findAll();
    List<Response> listRetorno = new ArrayList<Response>();

    for (Sample sample : listSample) {
      SampleResponse sr = new SampleResponse();
      sr.setName(sample.getName());
      listRetorno.add(sr);
    }

    return listRetorno;
  }

  @Override
  public Response add(SampleResponse sampleResponse) {
    // implement a transformer if you want to.
    Sample sample = new Sample();
    sample.setName(sampleResponse.getName());

    Key<Sample> key = dao.add(sample);
    
    MensagemResponse msg = new MensagemResponse();
    msg.setMsg(ResponseMessageEnum.OK.descricao());
    
    return msg;
  }

}
