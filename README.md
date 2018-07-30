Tech Gallery
==================

A skeleton application for Google Cloud Endpoints in Java.

## Products
- [App Engine][1]

## Language
- [Java][2]

## APIs
- [Google Cloud Endpoints][3]
- [Google App Engine Maven plugin][4]

## Setup Instructions

1. Update the value of `application` in `appengine-web.xml` to the app
   ID you have registered in the App Engine admin console and would
   like to use to host your instance of this sample.

1. Optional step: These sub steps are not required but you need this
   if you want to have auth protected methods.

    1. Update the values:
        1. `SERVICE_ACCOUNT_CLIENT_ID` | (From a Service Acc on GCP) 
        1. `WEB_CLIENT_ID` | (Create an OAuth acc on GCP to get credentials)
        
        in `src/main/java/${packageInPathFormat}/Constants.java`
       to reflect the respective client IDs you have registered in the
       [APIs Console][6]. 
       
    1. Update the values:
        1. `CLIENT_ID` | same used in `WEB_CLIENT_ID`
         
        in `src/main/webapp/modules/auth/index.js` to reflect the respective client IDs you have registered in the
        [APIs Console][6]. 

    1. You also need to supply the web client ID you have registered
       in the [APIs Console][4] to your client of choice (web, Android,
       iOS).

1. Run the application with `mvn appengine:run`, and ensure it's
   running by visiting your local server's api explorer's address (by
   default [localhost:8080/_ah/api/explorer][5].)

1. Deploy your application to Google App Engine with

   $ mvn appengine:deploy


[1]: https://developers.google.com/appengine
[2]: http://java.com/en/
[3]: https://developers.google.com/appengine/docs/java/endpoints/
[4]: https://developers.google.com/appengine/docs/java/tools/maven
[5]: https://localhost:8080/_ah/api/explorer

### Useful links

Quickstart for Debian and Ubuntu
https://cloud.google.com/sdk/docs/quickstart-debian-ubuntu

Migrating to Endpoints Frameworks for App Engine 
https://cloud.google.com/endpoints/docs/frameworks/java/migrating   

Setting Up a Development Environment
https://cloud.google.com/endpoints/docs/frameworks/java/set-up-environment

Adding API Management
https://cloud.google.com/endpoints/docs/frameworks/java/adding-api-management

Deploying and Testing an API
https://cloud.google.com/endpoints/docs/frameworks/java/test-deploy

Using Apache Maven and the App Engine Plugin (App Engine SDK-based) 
https://cloud.google.com/appengine/docs/standard/java/tools/maven

Using Apache Maven and the App Engine Plugin (Cloud SDK-based) 
https://cloud.google.com/appengine/docs/standard/java/tools/using-maven

App Engine Maven Plugin (Cloud SDK-based) Goals and Parameters
https://cloud.google.com/appengine/docs/standard/java/tools/using-maven

Cloud Endpoints - Required Files and Configuration 
https://cloud.google.com/endpoints/docs/frameworks/java/required_files

Endpoints Framework Maven plugin
https://cloud.google.com/endpoints/docs/frameworks/java/maven-endpoints-frameworks-plugin 

Known Issues 
https://cloud.google.com/endpoints/docs/frameworks/known-issues