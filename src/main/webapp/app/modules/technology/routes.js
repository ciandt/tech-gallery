module.exports = function(
  $stateProvider,
  $urlRouterProvider
) {

  /**
   * Views folder for the controller
   * @type {String}
   */
  var viewsFolder = 'app/modules/technology/views/';

  $urlRouterProvider
    .when('', '/technologies')
    .when('/', '/technologies');

  $stateProvider
    .state('root.technologies', {
      url: '/technologies',
      controller: 'TechnologiesController as technologies',
      templateUrl: viewsFolder + 'technologies.html'
    })
    .state('root.technologies.view', {
      url: '/technologies/:slug',
      controller: 'TechnologyController as technology'
    })
    .state('root.technologies.add', {
      url: '/technologies/new',
      controller: 'TechnologyAddController as technology'
    });
};
