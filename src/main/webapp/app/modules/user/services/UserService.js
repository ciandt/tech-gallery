module.exports = function ($rootScope, $q, $timeout) {

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

    // Mock with timeout for loading effect
    $timeout(function () {
      angular.copy({
        id: id,
        name: 'Fulano de Tal',
        image: 'https://lh5.googleusercontent.com/-Ggye___6JQU/AAAAAAAAAAI/AAAAAAAAABc/9udrCsEUoc4/photo.jpg?sz=50'
      }, context.profile);

      deferred.resolve(context.profile);
    }, 300);

    return deferred.promise;
  }

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
