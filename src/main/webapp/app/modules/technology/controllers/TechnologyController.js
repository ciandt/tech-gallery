module.exports = function ($scope, $rootScope, $stateParams, AppService, TechnologyService) {

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
    AppService.setPageTitle(technology.name);
    context.item = technology;
    context.loading = false;
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

  this.getEndorsementsByTech = function() {
    return TechnologyService.getEndorsementsByTech($stateParams.id);
  };

  this.endorseUser = function() {
    TechnologyService.endorseUser($stateParams.id, this.endorsed.email);
  };

  this.showSelfInformations = function(email){
    if($scope.userEmail == email){
      return true;
    }
    return false;
  };

  this.generateId = function(index, email) {
    return 'plusOne' + index + email;
  };

}
