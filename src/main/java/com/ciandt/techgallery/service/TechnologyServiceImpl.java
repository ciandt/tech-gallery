package com.ciandt.techgallery.service;

import java.util.ArrayList;
import java.util.List;

import com.ciandt.techgallery.persistence.dao.TechnologyDAO;
import com.ciandt.techgallery.persistence.dao.TechnologyDAOImpl;
import com.ciandt.techgallery.persistence.model.Technology;
import com.ciandt.techgallery.service.model.MessageResponse;
import com.ciandt.techgallery.service.model.Response;
import com.ciandt.techgallery.service.model.TechnologiesResponse;
import com.ciandt.techgallery.service.model.TechnologyResponse;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;

/**
 * 
 * @author felipers
 *
 */
public class TechnologyServiceImpl implements TechnologyService {

  TechnologyDAO technologyDAO = new TechnologyDAOImpl();

  @Override
  public Response addTechnology(TechnologyResponse technology)
      throws InternalServerErrorException, BadRequestException {
    String techName = technology.getName();

    if (techName == null || techName.equals("")) {
      throw new BadRequestException("Nome da Tecnologia não pode ser em branco!");
    } else {
      Technology entity = new Technology();
      entity.setName(techName);
      technologyDAO.add(entity);
      // set the id and return it
      technology.setId(entity.getId());
      return technology;
    }
  }

  @Override
  public Response getTechnologies() throws InternalServerErrorException, NotFoundException {
    // throw new InternalServerErrorException("Not implemented");
    // return (new MessageResponse(500, "Not implemented"));
    List<Technology> techEntities = technologyDAO.findAll();
    if (techEntities == null) {
      throw new NotFoundException("Não foi encontrado nenhuma tecnologia!");
    } else {
      TechnologiesResponse response = new TechnologiesResponse();
      List<TechnologyResponse> aux = new ArrayList<TechnologyResponse>();

      for (int i = 0; i < techEntities.size(); i++) {
        Technology tech = techEntities.get(i);
        TechnologyResponse aux2 = new TechnologyResponse();
        aux2.setId(tech.getId());
        aux2.setName(tech.getName());
        aux.add(aux2);
      }
      response.setTechnologies(aux);
      return response;
    }
  }

}
