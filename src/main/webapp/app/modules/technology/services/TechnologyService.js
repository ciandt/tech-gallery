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
   * @param  {String}   id The ID of the technology
   * @return {Promise} The gapi response
   */
   this.getTechnology = function (id) {
    var deferred = $q.defer();
    if (!id) {
      throw 'getTechnology needs a valid `id` parameter';
    }
    var req = {
      id : id
    };
    gapi.client.rest.getTechnology(req).execute(function(data){
      context.technology = data;
      deferred.resolve(context.technology);
    });
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

  this.getUserSkill = function (idTech) {
    var deferred = $q.defer();
    if (!idTech) {
      throw 'getTechnology needs a valid `id` parameter';
    }
    var req = {
      id : idTech
    };
    gapi.client.rest.getUserSkill(req).execute(function(data) {
      context.rating = data.value;
      deferred.resolve(context.rating);
    });
    return deferred.promise;
  }

  this.addUserSkill = function (idTech, newValue, oldValue) {
    var deferred = $q.defer();
    if (newValue !== oldValue) {
      if (!idTech) {
        throw 'getTechnology needs a valid `id` parameter';
      }
      var req = {
        technology : idTech,
        value : newValue
      };
      gapi.client.rest.addSkill(req).execute(function(data) {
        //ga('send', 'event', 'TechGalleryEvents', 'skill_add', $scope.name);
      });
    }
  }

  this.getRecommended = function () {
    // Mock
    return false;
  }

  this.endorsed = {};

  this.endorseUser = function(idTech, endorsedMail) {
    var req = {
      technology : idTech,
      endorsed : endorsedMail
    };
    if (req.endorsed) {
      gapi.client.rest.addEndorsement(req).execute(function(data) {

        /*
        * G+ Post
        */
        /*if($scope.postGooglePlus && !data.hasOwnProperty('error')){
          var req = {
            feature : featureEnum.ENDORSE,
            currentUserMail : data.endorser.email,
            endorsedMail : data.endorsed.email,
            technologyName : data.technology.name,
            appLink : $scope.currentPage
          }
          gapi.client.rest.postComment(req).execute();
        }*/

       /* var alert;
        if (data.hasOwnProperty('error')) {
          alert = alerts.failure;
          alert.msg = data.error.message;
        }else{
          alert = alerts.success;
        }
        $scope.alert = alert;
        $scope.endorsed = '';*/
      });
    }
    //$scope.$apply();
  };

/*
* Auto complete on edorse user.
*/
this.getUsersList = function (value){
  var req = {query:value};
  return gapi.client.rest.usersAutoComplete(req).then(function (data){
    for(var i in data.result.items){
      if(!data.result.items[i].photo){
        data.result.items[i].photo = "/assets/images/default-user-image.jpg";
      }
    }
    return data.result.items;
  });
}

  /**
  * Begin of Show Endorsement Features
  */
  this.showAllEndorsers = function(endorsers) {
    return (endorsers.length > 0);
  };

  this.getEndorsementsByTech = function(idTech) {
    var req = {
      id : idTech
    };
    gapi.client.rest.getEndorsementsByTech(req).execute(function(data) {
      if(data.result && data.result.endorsements){
        var response = data.result.endorsements;
        for(var i in response){
          var fullResponse = response[i].endorsers;
          var endorsersFiltered = fullResponse.slice(0,5);
          response[i].endorsersFiltered = endorsersFiltered;
          if(!response[i].endorsed.photo) {
            response[i].endorsed.photo = "/assets/images/default-user-image.jpg";
          }
          response[i].endorsers = this.setPlusOneClass(response[i].endorsers);
        }
      }
      /*$scope.showEndorsementResponse = response;
      $scope.processingEndorse = false;
      $scope.loadEndorsements = false;
      $scope.$apply();*/
    });
  }

  this.open = function(endorsers, size) {
    var modalInstance = $modal.open({
      animation : true,
      templateUrl : '/showEndorsementModal.html',
      controller : 'modalController',
      size : size,
      resolve : {
        endorsers : function() {
          return endorsers;
        }
      }
    });
  };

  /*
     *
     * Begin of +1 features
     *
     */
     this.setPlusOneClass = function(endorsers){
      for(var i in endorsers){
        if(endorsers[i].email == $scope.userEmail){
          endorsers.plusOneClass = 'btn GPlusAdded';
          return endorsers;
        }
      }
      endorsers.plusOneClass = 'btn GPlusDefault';
      return endorsers;
    };

  };
