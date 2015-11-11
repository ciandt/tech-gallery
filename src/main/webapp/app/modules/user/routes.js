module.exports = function(
  $stateProvider,
  $urlRouterProvider
) {

  /**
   * Views folder for the controller
   * @type {String}
   */
  var viewsFolder = 'app/modules/user/views/';

  $stateProvider
    .state('root.user', {
      url: '/people/:id',
      controller: 'UserController as user',
      templateUrl: viewsFolder + 'user.html'
    });
};
