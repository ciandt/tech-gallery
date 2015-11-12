module.exports = function ($rootScope, $stateParams, AppService, TechnologyService, $modal) {

  /**
   * Object context
   * @type {Object}
   */
   var context = this;

  /**
   * Loading state
   * @type {Boolean}
   */
   this.loading = true;

  /**
   * Technology details
   * @type {Object}
   */
   this.item = {};

  // Load techonlogy based on URL param
  TechnologyService.getTechnology($stateParams.id).then(function (technology) {
    if (technology.hasOwnProperty('error')) {
      context.showContent = false;
      context.showTechNotExists = true;
      return;
    }
    AppService.setPageTitle(technology.name);
    context.item = technology;
    context.loading = false;
    //loadComments();
  });

  this.ratings = TechnologyService.getRatings();

  this.rating = {};

  this.oldRating = {};

  // Load techonlogy based on URL param
  TechnologyService.getUserSkill($stateParams.id).then(function (rating) {
    context.rating = rating;
    context.oldRating = rating;
  });

  this.setSkill = function (technology, newRating) {
    TechnologyService.addUserSkill($stateParams.id, newRating, context.oldRating);
  }

  this.recommended = TechnologyService.getRecommended();

  this.setRecommendation = function (recommendation) {
    context.recommended = recommendation;
    document.getElementById('recommendation-comment-input').focus();
  }

  // @todo Move this to UserService
  this.share = {
    gplus : true
  }

  this.getUsersList = function(value) {
    return TechnologyService.getUsersList(value);
  };

  /**
   * Begin of show comments features
   */
  /*function loadComments() {
    var req = {technologyId: $stateParams.id};
    TechnologyService.getCommentsByTech($stateParams.id).then(function(data){
      context.techComments = data.comments;
      context.techCommentsFull = data.comments;
    });
  }*/

  /*this.addComment = function(){
      if(context.comment && context.comment.trim().length <= 500){
        if(context.recommended == undefined){
          var req = {
              technologyId : $stateParams.id,
              comment : context.comment
          };
          gapi.client.rest.addComment(req).execute(function(data) {
            $scope.processingComment = true;
            callBackLoaded();
            $scope.comment = '';
            if($scope.postGooglePlus && !data.hasOwnProperty('error')){
                  var req = {
                        feature : featureEnum.COMMENT,
                        currentUserMail : data.author.email,
                        technologyName : $scope.name,
                comment: data.comment,
              appLink: $scope.currentPage
                    }
                  gapi.client.rest.postComment(req).execute();
                }
          });
          ga('send', 'event', 'TechGalleryEvents', 'comment_add', $scope.name);
        }else {
          var req = {
              technology : {id : $stateParams.id},
              comment : {comment : context.comment},
              recommendation : {score : $scope.score}
          };
          gapi.client.rest.addRecommendationComment(req).execute(function(data) {
            $scope.processingComment = true;
            callBackLoaded();
            $scope.comment = '';
            $scope.score = undefined;
            $scope.setClassThumbs('');
            if($scope.postGooglePlus && !data.hasOwnProperty('error')){
                  var req = {
                      feature : featureEnum.RECOMMEND,
                      score : data.score,
                      currentUserMail : data.recommender.email,
                      technologyName : data.technology.name,
                      appLink: $scope.currentPage
                  }
                  gapi.client.rest.postComment(req).execute();
                }
          });
          ga('send', 'event', 'TechGalleryEvents', 'recommendation_add', $scope.name);
        }
      }else{
        if($scope.score !== undefined){
          $scope.alertComment = true;
          $scope.alertMsgComment = 'Você deve informar um comentário sobre sua recomendação.';
        }
      }
    }*/


    this.showAllEndorsers = function(endorsers) {
      return (endorsers.length > 0);
    };

  this.getEndorsementsByTech = function() {
   TechnologyService.getEndorsementsByTech($stateParams.id).then(function(data){
    context.showEndorsementResponse = data;
   });
  };

  this.endorseUser = function() {
    TechnologyService.endorseUser($stateParams.id, this.endorsed.email).then(function(data){
        if(!data.hasOwnProperty('error')){
          context.getEndorsementsByTech();
          //AppService.setAlert('Usuário indicado!' ,'success');
        } else {
          //AppService.setAlert(data.error.message ,'error');
        }
    });
  };

  this.showSelfInformations = function(email){
    if($rootScope.userEmail == email){
      return true;
    }
    return false;
  };

  this.generateId = function(index, email) {
    return 'plusOne' + index + email;
  };

  this.getEndorsementsByTech();

     this.followTechnology = function(idTechnology, $event){
    context.currentElement = $event.currentTarget;
      TechnologyService.followTechnology(idTechnology).then(function(data){
        if(!data.hasOwnProperty('error')){
          changeFollowedClass(context.currentElement);
        }
      });
   }

   function changeFollowedClass(element){
    if(element.className.indexOf('btn-default') > 0){
      element.className = 'btn btn-xs btn-danger';
    }else{
      element.className = 'btn btn-xs btn-default';
    }
    context.currentElement = undefined;
  }

  this.setFollowedClass = function(isFollowedByUser){
    if(isFollowedByUser){
      return 'btn btn-xs btn-danger';
    }
    return 'btn btn-xs btn-default';
  }

      this.addEndorse = function(endorsed, id){
      TechnologyService.addEndorse(endorsed, id, $stateParams.id).then(function(data){
        if(!data.hasOwnProperty('error')){
          //reload endorsers
          //change css class
        }
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
}
