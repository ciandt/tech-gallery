module.exports = function($q, $timeout, $rootScope) {

  /**
   * Object context
   * @type {Object}
   */
   var context = this;

  /**
   * Retrieve list of technologies
   * @return {Promise} The gapi response
   */
   this.getProjects = function () {
    var deferred = $q.defer();
    gapi.client.rest.getProjects().execute(function (data) {
     context.foundItems = data.projects;
     $rootScope.$broadcast('searchChange', {
        technologies: context.foundItems
      });
     deferred.resolve(context.foundItems);
   });
    return deferred.promise;
  };

  this.addOrUpdate = function(context){
    console.log("SERVICO");
    console.log(context);
    this.searched = false;
    var req = fillRequestToSave(context);
    var deferred = $q.defer();
    console.log(gapi.client.rest);
    gapi.client.rest.addProject(req).execute(function(data){
      console.log(data);
      deferred.resolve(data);
    });
    return deferred.promise;
  };

  this.deleteTechnology = function(idTechnology){
    var deferred = $q.defer();
    var req = {technologyId: idTechnology};
    gapi.client.rest.deleteTechnology(req).execute(function(data){
      deferred.resolve(data);
    });
    return deferred.promise;
  }

  /*
   * Function to fill the request to save the technology.
   */
   function fillRequestToSave(context) {
      var req = {
          "name" : context.name
      };
      return req;
  }

  function slugify(text){
    return text.toString().toLowerCase()
        .replace(/\s+/g, '_')           // Replace spaces with _
        .replace(/\#/g, '_')            // Replace # with _
        .replace(/\//g, '_')            // Replace / with _
        .replace(/[^\w\-]+/g, '')       // Remove all non-word chars
        .replace(/\-\-+/g, '-')         // Replace multiple - with single -
        .replace(/^-+/, '')             // Trim - from start of text
        .replace(/-+$/, '');            // Trim - from end of text

  }

  this.slugify = function(text){
    return slugify(text);
  }

  
};
