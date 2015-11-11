module.exports = function(
  $stateProvider,
  $urlRouterProvider
) {

  /**
   * Views folder for the controller
   * @type {String}
   */
  var viewsFolder = 'app/modules/user/views/';

  $urlRouterProvider
    .when('/people', '/404')
    .when('/people/', '/404');

  $stateProvider
    .state('root.user', {
      url: '/people/:id',
      controller: 'UserController as user',
      templateUrl: viewsFolder + 'user.html'
    });
};
