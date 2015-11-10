module.exports = function($q) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * The list of technologies
   * @type {Array}
   */
  this.technologies = [];

  /**
   * Retrieve list of technologies
   * @return {Promise} The gapi response
   */
  this.getTechnologies = function () {
    var deferred = $q.defer();
    gapi.client.rest.getTechnologies().execute(function (data) {
       gapi.client.rest.handleLogin().execute();
       context.technologies = data.technologies;
       deferred.resolve(context.technologies);
    });
    return deferred.promise;
  };

  this.addOrUpdate = function(context){
    var req = fillRequestToSave(context);
	  var deferred = $q.defer();
	  gapi.client.rest.addOrUpdateTechnology(req).execute(function(data){
		  deferred.resolve(data);
      });
	  return deferred.promise;
  };

  /*
   * Function to fill the request to save the technology.
   */
  function fillRequestToSave(context) {
    if(context.image && context.image.startsWith('https://')){
          var req = {
              id : slugify(context.name),
              name : context.name,
              shortDescription : context.shortDescription,
              recommendationJustification : context.justification,
              recommendation : context.selectedRecommendation,
              description : context.description,
              website : context.webSite,
              image : context.image
          };
          return req;
        }else{
          var req = {
              id : slugify(context.name),
              name : context.name,
              shortDescription : context.shortDescription,
              recommendationJustification : context.justification,
              recommendation : context.selectedRecommendation,
              description : context.description,
              website : context.webSite,
              imageContent : context.image
          };
          return req;
        }
  }

  function slugify(text){
    return text.toString().toLowerCase()
        .replace(/\s+/g, '-')           // Replace spaces with -
        .replace(/[^\w\-]+/g, '')       // Remove all non-word chars
        .replace(/\-\-+/g, '-')         // Replace multiple - with single -
        .replace(/^-+/, '')             // Trim - from start of text
        .replace(/-+$/, '');            // Trim - from end of text
  }

  this.searchTechnologies = function(req){
    var deferred = $q.defer();
    gapi.client.rest.findByFilter(req).execute(function(data){
      var foundTechnologies = data.technologies;
      deferred.resolve(foundTechnologies);
    });
    return deferred.promise;
  }

  /**
   * The single technology
   * @type {Object}
   */
  this.technology = {};

  /**
   * Retrive a technology based on its ID
   * @param  {String} id The ID of the technology
   * @return {Void}
   */
  this.getTechnology = function (id) {
	  var deferred = $q.defer();
	  if(id){
		  var req = {id: id};
		  gapi.client.rest.getTechnology(req).execute(function(data){
			  deferred.resolve(data);
		  });
	  }
	  return deferred.promise;
  }

  /**
   * Ratings for user skill on a technology
   * @return {Array} The list of rating objects with value and title
   */
  this.getRatings = function () {
    return [
      {
        value: 1,
        title : 'Newbie'
      },
      {
        value: 2,
        title : 'Iniciante'
      },{
        value: 3,
        title : 'Padawan'
      },{
        value: 4,
        title : 'Knight'
      },{
        value: 5,
        title : 'Jedi'
      },
    ];
  }

  this.getRecommended = function () {
    // Mock
    return false;
  }
};
