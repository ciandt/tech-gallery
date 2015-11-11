module.exports = function ($rootScope, $q, $timeout, TechnologyService) {

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

    // Mock
    angular.copy({
      id: id,
      name: 'Fulano de Tal',
      image: 'https://lh5.googleusercontent.com/-Ggye___6JQU/AAAAAAAAAAI/AAAAAAAAABc/9udrCsEUoc4/photo.jpg?sz=50',
      technologiesCount: 12,
      recommendationsCount: 23,
      commentsCount: 108,
      // technologies : []
      technologies : [
        {
          id: 'vagrant',
          name: 'Vagrant',
          endorsementsCount: 123,
          rating: {
            value : 5,
            title : 'Jedi'
          },
          recommendation: {
            recommended: true,
            comment: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quam a ex voluptatem delectus quo hic nemo provident modi excepturi illum!'
          }
        },
        {
          id: 'webgel',
          name: 'WebGL',
          endorsementsCount: 3,
          rating: {
            value : 3,
            title : 'Padawan'
          },
          recommendation: {
            recommended: false,
            comment: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores, earum quisquam omnis vero doloribus illo!'
          }
        },
        {
          id: 'unity',
          name: 'Unity',
          endorsementsCount: 1234567,
          rating: {
            value : 1,
            title : 'Newbie'
          },
          recommendation: {
            recommended: true,
            comment: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptas provident sint voluptatibus\n commodi recusandae dicta, harum vitae repellendus. Aut, reiciendis quis harum! Ad, quos, officia.'
          }
        },
        {
          id: 'sass',
          name: 'SASS',
          endorsementsCount: 75,
          rating: {
            value : 5,
            title : 'Jedi'
          },
          recommendation: {
            recommended: true,
            comment: 'Lorem ipsum dolor sit amet.'
          }
        },
        {
          id: 'vagrant',
          name: 'Vagrant',
          endorsementsCount: 123,
          rating: {
            value : 5,
            title : 'Jedi'
          },
          recommendation: {
            recommended: true,
            comment: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quam a ex voluptatem delectus quo hic nemo provident modi excepturi illum!'
          }
        },
        {
          id: 'webgel',
          name: 'WebGL',
          endorsementsCount: 3,
          rating: {
            value : 3,
            title : 'Padawan'
          },
          recommendation: {
            recommended: false,
            comment: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores, earum quisquam omnis vero doloribus illo!'
          }
        },
        {
          id: 'unity',
          name: 'Unity',
          endorsementsCount: 1234567,
          rating: {
            value : 1,
            title : 'Newbie'
          },
          recommendation: {
            recommended: true,
            comment: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptas provident sint voluptatibus\n commodi recusandae dicta, harum vitae repellendus. Aut, reiciendis quis harum! Ad, quos, officia.'
          }
        },
        {
          id: 'sass',
          name: 'SASS',
          endorsementsCount: 75,
          rating: {
            value : 5,
            title : 'Jedi'
          },
          recommendation: {
            recommended: true,
            comment: 'Lorem ipsum dolor sit amet.'
          }
        }
      ]
    }, context.profile);

    deferred.resolve(context.profile);
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
