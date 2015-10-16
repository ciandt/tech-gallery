package com.ciandt.techgallery.utils;

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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Handler to connect with the Storage.
 *
 * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
 * @since 15/10/2015
 *
 */
public class StorageHandler {

  /*
   * Constants --------------------------------------------
   */
  private static final Logger logger = Logger.getLogger(StorageHandler.class.getName());
  private static final String ALL_USERS = "allUsers";
  private static final String READER = "READER";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static final String APPLICATION_NAME = SystemProperty.applicationId.get();
  private static final String LOCATION = "US";
  private static final String BUCKET_NAME = SystemProperty.applicationId.get() + "-";
  private static final String FULL = "full";
  private static final String PROJECT_EDITORS = "project-editors-";
  private static final String WRITER = "WRITER";

  /*
   * Attributes --------------------------------------------
   */
  private static StorageHandler instance;
  private static Storage storageService;

  /*
   * Constructors --------------------------------------------
   */
  public StorageHandler() {}

  /**
   * Singleton method for the DAO.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 15/10/2015
   *
   * @return StorageHandler instance.
   */
  public static StorageHandler getInstance() {
    if (instance == null) {
      instance = new StorageHandler();
    }
    return instance;
  }

  /*
   * Methods --------------------------------------------
   */
  /**
   * Method to insert a image into the bucket of the cloud storage.
   *
   * @author <a href="mailto:joaom@ciandt.com"> João Felipe de Medeiros Moreira </a>
   * @since 15/10/2015
   *
   * @param name of the image.
   * @param contentStream to be converted.
   *
   * @return the media link of the image.
   *
   * @throws IOException in case a IO problem.
   * @throws GeneralSecurityException in case a security problem.
   */
  public static String saveImage(String name, InputStreamContent contentStream)
      throws IOException, GeneralSecurityException {
    logger.finest("###### Saving a image");
    StorageObject objectMetadata = new StorageObject()
        // Set the destination object name
        .setName(name)
        // Set the access control list to publicly read-only
        .setAcl(Arrays.asList(new ObjectAccessControl().setEntity(ALL_USERS).setRole(READER)));

    Storage client = getService();
    String bucketName = getBucket().getName();
    Storage.Objects.Insert insertRequest =
        client.objects().insert(bucketName, objectMetadata, contentStream);

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
  private static Bucket getBucket() throws IOException, GeneralSecurityException {
    logger.finest("###### Getting the bucket");
    String applicationVersion = TechGalleryUtil.getApplicationVersion();
    Bucket createdBucket = getExistingBucket(applicationVersion);
    if (createdBucket == null) {
      return createBucket(applicationVersion);
    }
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
    logger.finest("###### Creating the bucket");
    Storage client = getService();
    Bucket newBucket = new Bucket().setName(BUCKET_NAME + applicationVersion).setLocation(LOCATION)
        .setAcl(Arrays.asList(new BucketAccessControl().setEntity(ALL_USERS).setRole(READER),
            new BucketAccessControl().setEntity(PROJECT_EDITORS + TechGalleryUtil.getAppId())
                .setRole(WRITER)));
    Storage.Buckets.Insert bucketToCreate = client.buckets().insert(APPLICATION_NAME, newBucket);
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
    logger.finest("###### Searching a bucket");
    Storage client = getService();
    Storage.Buckets.Get bucketRequest = client.buckets().get(BUCKET_NAME + bucketName);
    // Fetch the full set of the bucket's properties (e.g. include the ACLs in the response)
    bucketRequest.setProjection(FULL);
    try {
      return bucketRequest.execute();
    } catch (Exception e) {
      // If the bucket doesn't exists, return null to create a new one.
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
    logger.finest("###### Getting the storage service");
    if (null == storageService) {
      HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
      GoogleCredential credential = GoogleCredential.getApplicationDefault();
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
