module.exports = function($q, $timeout, $rootScope) {

  /**
   * Object context
   * @type {Object}
   */
   var context = this;

  var featureEnum = {
    ENDORSE: 'ENDORSE',
    COMMENT: 'COMMENT',
    RECOMMEND: 'RECOMMEND'
  };

   this.setTextFilter = function(textSearch){
    context.textSearch = textSearch;
  };

  this.setContentFilters = function(selectedRecommendationFilter, selectedOrderFilter, selectedLastActivityFilter){
    context.selectedRecommendationFilter = selectedRecommendationFilter;
    context.selectedOrderFilter = selectedOrderFilter;

    context.selectedLastActivityFilter = selectedLastActivityFilter;

    context.searchTechnologies();
  }

  /**
   * Retrieve list of technologies
   * @return {Promise} The gapi response
   */
   this.getTechnologies = function () {
    var deferred = $q.defer();
    gapi.client.rest.getTechnologies().execute(function (data) {
     context.foundItems = data.technologies;
     $rootScope.$broadcast('searchChange', {
        technologies: context.foundItems
      });
     deferred.resolve(context.foundItems);
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

  this.followTechnology = function(technologyId){
    var deferred = $q.defer();
    var req = {technologyId:technologyId};
    gapi.client.rest.followTechnology(req).execute(function(data){
      deferred.resolve(data);
    });
    return deferred.promise;
  }

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
        .replace(/\s+/g, '_')           // Replace spaces with -
        .replace(/[^\w\-]+/g, '')       // Remove all non-word chars
        .replace(/\-\-+/g, '-')         // Replace multiple - with single -
        .replace(/^-+/, '')             // Trim - from start of text
        .replace(/-+$/, '');            // Trim - from end of text
  }

  this.slugify = function(text){
    return slugify(text);
  }

  this.searchTechnologies = function(){
    context.foundItems = [];
    var req = {
      titleContains: context.textSearch,
      shortDescriptionContains: context.textSearch,
      orderOptionIs: context.selectedOrderFilter,
      dateFilter : context.selectedLastActivityFilter,
      recommendationIs: context.selectedRecommendationFilter
    }
    var deferred = $q.defer();
    gapi.client.rest.findByFilter(req).execute(function(data){
      context.foundItems = data.technologies;
      $rootScope.$broadcast('searchChange', {
        technologies: context.foundItems
      });
      deferred.resolve(context.foundItems);
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

  this.getRating = function (rating) {
    context.getRatings().filter(function (i) {
      return i.value === rating;
    })
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
    if (newValue !== oldValue) {
      if (!idTech) {
        throw 'getTechnology needs a valid `id` parameter';
      }
      var req = {
        technology : idTech,
        value : newValue
      };
      gapi.client.rest.addSkill(req).execute(function(data) {
      });
    }
  }

  this.getRecommended = function () {
    return true;
  }

  this.endorsed = {};

  this.endorseUser = function(idTech, endorsedMail) {
    var deferred = $q.defer();
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
        deferred.resolve(data);
      });
    }
    //$scope.$apply();
    return deferred.promise;
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
    var deferred = $q.defer();
    var req = {
      id : idTech
    };
    gapi.client.rest.getEndorsementsByTech(req).execute(function(data) {
      if(data.result && data.result.endorsements){
        var response = data.result.endorsements;
        for(var i in response){
          var fullResponse = response[i].endorsers;
          if(fullResponse){
	          var endorsersFiltered = fullResponse.slice(0,5);
	          response[i].endorsersFiltered = endorsersFiltered;
	          if(!response[i].endorsed.photo) {
	            response[i].endorsed.photo = "/assets/images/default-user-image.jpg";
	          }
	          response[i].endorsers = setPlusOneClass(response[i].endorsers);
          }
        }
      }
      deferred.resolve(response);
    });
    return deferred.promise;
  }

  this.getRecommendations = function(){
    var deferred = $q.defer();
    gapi.client.rest.getRecommendations().execute(function(data){
      deferred.resolve(data.items);
    });
    return deferred.promise;
  }

  this.getCommentsByTech = function(technologyId){
    var deferred = $q.defer();
    var req = {technologyId: technologyId};
    gapi.client.rest.getCommentsByTech(req).execute(function(data){
      deferred.resolve(data);
    });
    return deferred.promise;
  }
  /*
  * Begin of +1 features
  */
  function setPlusOneClass(endorsers){
    for(var i in endorsers){
      if(endorsers[i].email == $rootScope.userEmail){
        endorsers.plusOneClass = 'btn-danger';
        return endorsers;
      }
    }
    endorsers.plusOneClass = '';
    return endorsers;
  };

  this.addComment = function(context, id){
    var deferred = $q.defer();
    var req = {
      technologyId : id,
      comment : context.comment
    };
    gapi.client.rest.addComment(req).execute(function(data) {
      deferred.resolve(data);
      if(context.postGooglePlus && !data.hasOwnProperty('error')){
        var req = {
          feature : featureEnum.COMMENT,
          currentUserMail : data.author.email,
          technologyName : context.name,
          comment: data.comment,
          appLink: context.currentPage
        }
        //gapi.client.rest.postComment(req).execute();
      }
    });
    return deferred.promise;
  };

  this.addRecommendationComment = function(context, id){
    var deferred = $q.defer();
    var req = {
      technology : {id : id},
      comment : {comment : context.commentRecommend},
      recommendation : {score : context.recommended}
    };
    gapi.client.rest.addRecommendationComment(req).execute(function(data) {
      deferred.resolve(data);
      if(context.postGooglePlus && !data.hasOwnProperty('error')){
        var req = {
          feature : featureEnum.RECOMMEND,
          score : data.score,
          currentUserMail : data.recommender.email,
          technologyName : data.technology.name,
          appLink: context.currentPage
        }
        //gapi.client.rest.postComment(req).execute();
      }
    });
    return deferred.promise;
  };

  this.addEndorse = function(endorsed, id, idTech) {
    //$scope.disablePlusOne = true;
    //$scope.processingEndorse = true;
    var deferred = $q.defer();
    var completeEmail = endorsed.email;
    completeEmail = completeEmail.split('@');
    var email = completeEmail[0];
    var req = {};
    req.endorsed = email;
    req.technology = idTech;
    gapi.client.rest.addEndorsementPlusOne(req).execute(function(data){
      deferred.resolve(data);
    });
    return deferred.promise;
  };
};
