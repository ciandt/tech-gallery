module.exports = function ($rootScope, $q) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  this.getUserEmail = function(callBackFunction, authResult){
    setTimeout(function(){
      gapi.client.load('oauth2', 'v2', function() {
        gapi.client.oauth2.userinfo.get().execute(function(resp) {
          $rootScope.userEmail = resp.email;
          //if(userEmail){
          //  trackUser(userEmail.replace('@'+resp.hd, ''));
          //}
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

  function trackUser(userEmail) {
    ga('create', 'UA-60744312-3', 'auto');
    ga('set', '&uid', userEmail);
    ga('set', 'contentGroup1', userEmail);
    ga('set', 'dimension1', userEmail);
    ga('send', 'pageview');
  }
}