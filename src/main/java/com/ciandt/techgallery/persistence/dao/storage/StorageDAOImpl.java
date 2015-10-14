package com.ciandt.techgallery.persistence.dao.storage;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.BucketAccessControl;
import com.google.api.services.storage.model.ObjectAccessControl;
import com.google.api.services.storage.model.StorageObject;
import com.google.appengine.api.utils.SystemProperty;

import com.ciandt.techgallery.persistence.dao.StorageDAO;
import com.ciandt.techgallery.utils.TechGalleryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that implements the StorageDAO.
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 13/10/2015
 *
 */
public class StorageDAOImpl implements StorageDAO {

  /*
   * Constants --------------------------------------------
   */
  private static final Logger logger = Logger.getLogger(StorageDAOImpl.class.getName());
  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String APPLICATION_NAME = SystemProperty.applicationId.get();
  private static final String LOCATION = "US";
  private static final String BUCKET_NAME = "tech-gallery-";

  /*
   * Attributes --------------------------------------------
   */
  private static Storage storageService;
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
    InputStreamContent contentStream = new InputStreamContent("image/png", stream);
    StorageObject objectMetadata = new StorageObject()
        // Set the destination object name
        .setName(name)
        // Set the access control list to publicly read-only
        .setAcl(Arrays.asList(new ObjectAccessControl().setEntity("allUsers").setRole("READER")));

    Storage client = getService();
    String bucketName = getBucket().getName();
    logger.log(Level.SEVERE, "##-- " + bucketName);
    Storage.Objects.Insert insertRequest =
        client.objects().insert(BUCKET_NAME + "joaom", objectMetadata, contentStream);

    return insertRequest.execute().getMediaLink();
  }

  /**
   * Method to get the bucket of the application version.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @return the bucket of this application version.
   *
   * @throws IOException in case a IO problem.
   * @throws GeneralSecurityException in case a security problem.
   */
  private Bucket getBucket() throws IOException, GeneralSecurityException {
    String applicationVersion = TechGalleryUtil.getApplicationVersion();
    Bucket createdBucket = getExistingBucket(applicationVersion);
    if (createdBucket == null) {
      return createBucket(applicationVersion);
    }
    logger.log(Level.INFO, "##-- Encontrou bucket");
    return createdBucket;
  }

  /**
   * Method to create a new bucket on cloud storage.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @param applicationVersion to create the bucket.
   *
   * @return the bucket created.
   *
   * @throws IOException in case a IO problem.
   * @throws GeneralSecurityException in case a security problem.
   */
  private static Bucket createBucket(String applicationVersion)
      throws IOException, GeneralSecurityException {
    Storage client = getService();
    Bucket newBucket =
        new Bucket().setName(BUCKET_NAME + applicationVersion).setLocation(LOCATION)
            .setAcl(Arrays.asList(new BucketAccessControl().setEntity("allUsers")
                .setRole("READER"),
            new BucketAccessControl().setEntity("project-editors-146680675139").setRole("WRITER")));
    Storage.Buckets.Insert bucketToCreate = client.buckets().insert(APPLICATION_NAME, newBucket);
    logger.log(Level.INFO, "##-- Criará bucket");
    return bucketToCreate.execute();
  }

  /**
   * Method to get a bucket that already exists.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @param bucketName to be founded.
   *
   * @return the bucket founded.
   *
   * @throws IOException in case a IO problem.
   * @throws GeneralSecurityException in case a security problem.
   */
  public static Bucket getExistingBucket(String bucketName)
      throws IOException, GeneralSecurityException {
    Storage client = getService();
    Storage.Buckets.Get bucketRequest = client.buckets().get(BUCKET_NAME + bucketName);
    // Fetch the full set of the bucket's properties (e.g. include the ACLs in the response)
    bucketRequest.setProjection("full");
    try {
      logger.log(Level.INFO, "##-- Buscará bucket");
      return bucketRequest.execute();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Method to create the service or get the service if is already created.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 13/10/2015
   *
   * @return the Storage service already created.
   *
   * @throws IOException in case a IO problem.
   * @throws GeneralSecurityException in case a security problem.
   */
  private static Storage getService() throws IOException, GeneralSecurityException {
    if (null == storageService) {
      // GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
      // new InputStreamReader(StorageDAOImpl.class.getResourceAsStream("/client_secret.json")));

      HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
      GoogleCredential credential = GoogleCredential.getApplicationDefault();
      // GoogleCredential credential = new
      // GoogleCredential.Builder().setClientSecrets(clientSecrets)
      // .setTransport(httpTransport).setJsonFactory(JSON_FACTORY).build();
      // Depending on the environment that provides the default credentials (e.g. Compute Engine,
      // App Engine), the credentials may require us to specify the scopes we need explicitly.
      // Check for this case, and inject the Cloud Storage scope if required.
      if (credential.createScopedRequired()) {
        credential = credential.createScoped(StorageScopes.all());
      }
      storageService = new Storage.Builder(httpTransport, JSON_FACTORY, credential)
          .setApplicationName(APPLICATION_NAME).build();
    }
    return storageService;
  }
}
