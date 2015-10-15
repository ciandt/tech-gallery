package com.ciandt.techgallery.persistence.dao.storage;

import com.google.api.client.http.InputStreamContent;

import com.ciandt.techgallery.persistence.dao.StorageDAO;
import com.ciandt.techgallery.utils.StorageHandler;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

/**
 * Class that implements the StorageDAO.
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 13/10/2015
 *
 */
public class StorageDAOImpl implements StorageDAO {

  private static final String IMAGE_FORMAT = "image/png";

  /*
   * Attributes --------------------------------------------
   */
  private static StorageDAOImpl instance;

  /*
   * Constructors --------------------------------------------
   */
  public StorageDAOImpl() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @return StorageDAOImpl instance.
   */
  public static StorageDAOImpl getInstance() {
    if (instance == null) {
      instance = new StorageDAOImpl();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  @Override
  public String insertImage(String name, InputStream stream)
      throws IOException, GeneralSecurityException {
    InputStreamContent contentStream = new InputStreamContent(IMAGE_FORMAT, stream);

    return StorageHandler.saveImage(name, contentStream);
  }
}
