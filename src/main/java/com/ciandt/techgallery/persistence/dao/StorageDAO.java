package com.ciandt.techgallery.persistence.dao;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

/**
 * Class to acess the storage of the application.
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 13/10/2015
 *
 */
public interface StorageDAO {

  /**
   * Method to insert a image into the bucket of the cloud storage.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @param name of the image.
   * @param contentType of the image.
   * @param stream to be converted.
   *
   * @return the self link of the image.
   *
   * @throws IOException in case a IO problem.
   * @throws GeneralSecurityException in case a security problem.
   */
  public String insertImage(String name, InputStream stream)
      throws IOException, GeneralSecurityException;
}
