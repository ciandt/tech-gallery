package com.ciandt.techgallery.persistence.example;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ciandt.techgallery.persistence.dao.UserGroupDAO;
import com.ciandt.techgallery.persistence.dao.UserGroupDAOImpl;
import com.ciandt.techgallery.persistence.model.UserGroup;
import com.ciandt.techgallery.sample.persistence.dao.RecommendationDAOImpl;
import com.ciandt.techgallery.sample.persistence.dao.TechnologyDAOImpl;
import com.ciandt.techgallery.sample.persistence.model.Recommendation;
import com.ciandt.techgallery.sample.persistence.model.Technology;
import com.googlecode.objectify.Key;

public class DAORelacaoOneToMany extends HttpServlet {
  @SuppressWarnings("unused")
  private static final Logger log = Logger.getLogger(DAORelacaoOneToMany.class.getName());

  /**
   * GET request for getting groups.
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    TechnologyDAOImpl techDAO = new TechnologyDAOImpl();

    List<Technology> allTechs = techDAO.findAll();
    for (int i = 0; i < allTechs.size(); i++) {
      System.out.println("Tech " + allTechs.get(i).getName() + " id: " + allTechs.get(i).getId());
    }

    RecommendationDAOImpl recDAO = new RecommendationDAOImpl();
    List<Recommendation> allRecs = recDAO.findAll();
    if (allRecs.size() > 0) {
      Recommendation recTeste = allRecs.get(0);
      List<Technology> allTechc2 = techDAO.findTechnologiesByRecommendation(recTeste);

      for (Technology tec : allTechc2) {
        System.out.println("Tech de teste: " + tec.getName() + " id: " + tec.getId());
      }

    }

  }

  /**
   * POST request for saving groups.
   */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    TechnologyDAOImpl techDAO = new TechnologyDAOImpl();

    Technology t = new Technology();
    t.setName("Angular");
    techDAO.addTechnology(t);
    Key<Technology> key = Key.create(Technology.class, t.getId());

    RecommendationDAOImpl recDAO = new RecommendationDAOImpl();
    for (int i = 0; i < 10; i++) {
      Recommendation rec = new Recommendation();
      rec.setScore("" + i);
      rec.setTechnology(key);
      recDAO.addRecommendation(rec);
    }

  }
}
