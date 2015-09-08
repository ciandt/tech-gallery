package com.ciandt.techgallery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ciandt.techgallery.persistence.dao.EndorsementDAO;
import com.ciandt.techgallery.persistence.dao.EndorsementDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAO;
import com.ciandt.techgallery.persistence.dao.TechGalleryUserDAOImpl;
import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.model.Endorsement;
import com.ciandt.techgallery.persistence.model.TechGalleryUser;
import com.ciandt.techgallery.service.model.EndorsementsGroupedByEndorsedTransient;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;


/**
 * Test for the EndorsementService
 * 
 * @author Daniel Eduardo
 */
public class EndorsementServiceTest {

  private static final Logger log = Logger.getLogger(EndorsementServiceTest.class.getName());

  /** user dao for getting users. */
  TechGalleryUserDAO userDAO = new TechGalleryUserDAOImpl();

  /** technology dao for getting technologies. */
  TechnologyDAO techDAO = new TechnologyDAOImpl();

  /** endorsement dao. */
  EndorsementDAO endorsementDAO = new EndorsementDAOImpl();

  EndorsementServiceImpl service = new EndorsementServiceImpl();

  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
      new LocalDatastoreServiceTestConfig());

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
  public void executaAgrupamento() {
    // TODO Fazer o teste funcionar com o objetify
    List<Endorsement> endorsements = createEndorsementList();
    List<EndorsementsGroupedByEndorsedTransient> groupEndorsementByEndorsed =
        service.groupEndorsementByEndorsed(endorsements);
    groupEndorsementByEndorsed.get(0);
  }

  private List<Endorsement> createEndorsementList() {
    // TODO incrementar para um cenário de teste mais completo
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
