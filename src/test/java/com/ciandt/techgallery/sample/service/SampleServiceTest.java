package com.ciandt.techgallery.sample.service;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ciandt.techgallery.sample.service.model.SampleResponse;


/**
 * SampleServiceTest demonstration.
 * 
 * @see <a href="Assert">http://junit.org/apidocs/org/junit/Assert.html</a>
 * 
 * @author Felipe Goncalves de Castro 
 */
public class SampleServiceTest {
  
  SampleServiceImpl sampleService = null;
  
  @Before
  public void contructObjects(){
    //you may prepare something before the tests start running.
    sampleService = new SampleServiceImpl();
  }
  
  @Test
  public void coolReturnTest() {
    SampleResponse sr = new SampleResponse();
    sr.setName("Test");
    
    String expected = "cool";
    
    SampleResponse srReturn = sampleService.doSomeCoolThing(sr);
    Assert.assertNotNull(srReturn);
    assertEquals(expected, srReturn.getName());
  }
  
  @Test
  public void nullReturnTest() {
    SampleResponse sr = null;
    
    SampleResponse srReturn = sampleService.doSomeCoolThing(sr);
    Assert.assertNull(srReturn);
  }

}
