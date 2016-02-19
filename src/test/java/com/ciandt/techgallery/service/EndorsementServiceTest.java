package com.ciandt.techgallery.service;

import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import com.ciandt.techgallery.persistence.dao.EndorsementDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.impl.EndorsementDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.dao.impl.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.impl.EndorsementServiceImpl;
import com.ciandt.techgallery.service.model.EndorsementsGroupedByEndorsedTransient;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Test for the EndorsementService.
 *
 * @author Daniel Eduardo
 */
public class EndorsementServiceTest {

  // private static final Logger log = Logger.getLogger(EndorsementServiceTest.class.getName());

  /** user dao for getting users. */
  TechGalleryUserDAO userDAO = TechGalleryUserDAOImpl.getInstance();

  /** technology dao for getting technologies. */
  TechnologyDAO techDAO = TechnologyDAOImpl.getInstance();

  /** endorsement dao. */
  EndorsementDAO endorsementDAO = EndorsementDAOImpl.getInstance();

  EndorsementServiceImpl service = EndorsementServiceImpl.getInstance();

  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  /**
   * Setup method for the test.
   */
  @Before
  public void setUp() {
    helper.setUp();
    ObjectifyService.register(TechGalleryUser.class);
    ObjectifyService.register(Endorsement.class);
    ObjectifyService.begin();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  @Ignore
  public void executaAgrupamento() throws BadRequestException, NotFoundException,
      InternalServerErrorException, OAuthRequestException {
    // TODO Make the test work with objetify
    List<Endorsement> endorsements = createEndorsementList();
    List<EndorsementsGroupedByEndorsedTransient> groupEndorsementByEndorsed =
        service.groupEndorsementByEndorsed(endorsements, null);
    groupEndorsementByEndorsed.get(0);
  }

  private List<Endorsement> createEndorsementList() {
    // TODO increase to a more complete test scenario
    TechGalleryUser endorsed = new TechGalleryUser();
    endorsed.setName("GOKU");
    endorsed.setId(1L);
    TechGalleryUser endorser = new TechGalleryUser();
    endorser.setId(2L);
    endorser.setName("FELIPE");


    Endorsement endorsement = new Endorsement();
    endorsement.setId(3L);
    endorsement.setEndorsed(Ref.create(endorsed));
    endorsement.setEndorser(Ref.create(endorser));

    List<Endorsement> list = new ArrayList<Endorsement>();
    list.add(endorsement);

    return list;
  }
}
