module.exports = function ($rootScope, $q, $timeout, TechnologyService, Analytics) {

  /**
   * Object context
   * @type {Object}
   */
   var context = this;

  /**
   * The user profile info
   * @type {Object}
   */
   this.profile = {};

  /**
   * Update the user profile
   * @param  {String} id The user ID
   * @return {Promise}
   */
   this.updateUserProfile = function (id) {
    var deferred = $q.defer();

    var userMail = id + "@ciandt.com";
    // var userMail = 'example@example.com';

    var req = {
     email : userMail
   };

   gapi.client.rest.profile.get(req).execute(function(data) {
      if(data && !data.hasOwnProperty('error')){
        var technologiesCount = 0;
        var recommendationsCount = 0;
        var commentsCount = 0;
        if(data.technologies){
          var technologiesCount = data.technologies.length;
          for (var i=0; i < data.technologies.length; i++) {
            if(data.technologies[i].recommendation){
              recommendationsCount++;
            }
            if(data.technologies[i].comments){
              commentsCount += data.technologies[i].comments.length;
            }
            var skillLevel = data.technologies[i].skillLevel;
            var rating = {
              value : 0,
              title : ''
            };
            if(skillLevel){
              rating = TechnologyService.getRatings()[skillLevel - 1];
            }
            data.technologies[i].rating = rating;
            data.technologies[i].id = TechnologyService.slugify(data.technologies[i].technologyName);
          };
        }

        data.owner.technologiesCount = technologiesCount;
        data.owner.recommendationsCount = recommendationsCount;
        data.owner.commentsCount = commentsCount;
      }
      deferred.resolve(data);
    });
  return deferred.promise;
}

this.getUserEmail = function(callBackFunction, authResult){
  setTimeout(function(){
    gapi.client.load('oauth2', 'v2', function() {
      gapi.client.oauth2.userinfo.get().execute(function(resp) {
        $rootScope.userEmail = resp.email;
          if(authResult == undefined && $rootScope.userEmail){
        	  Analytics.trackUser($rootScope.userEmail.replace('@'+resp.hd, ''));
          }
          if(callBackFunction){
            callBackFunction(authResult);
          }
        })
    });
  },200);
}

this.getUserInformations = function(){
  var deferred = $q.defer();
  gapi.client.rest.getLoggedUser().execute(function(data) {
    $rootScope.loggedUserInformation = data;
    $rootScope.loggedUserInformation.postGooglePlus = data.postGooglePlusPreference;
    deferred.resolve($rootScope.loggedUserInformation);
  });
  return deferred.promise;
}

this.logOutUser = function(){
  var logoutRedirect = 'https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue='
  logoutRedirect += location.protocol;
  logoutRedirect += '//';
  logoutRedirect += location.hostname;
  logoutRedirect += location.pathname;
  logoutRedirect += location.search;
  return logoutRedirect;
}
}
