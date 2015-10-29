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
    .state('root.technologies-view', {
      url: '/technologies/:slug',
      controller: 'TechnologyController as technology',
      templateUrl: viewsFolder + 'technology.html'
    })
    .state('root.technologies-edit', {
      url: '/technologies/:slug/edit',
      controller: 'TechnologyEditController as technology',
      templateUrl: viewsFolder + 'technology-add.html'
    })
    .state('root.technologies-add', {
      url: '/technologies/new',
      controller: 'TechnologyAddController as technology',
      templateUrl: viewsFolder + 'technology-add.html'
    });
};
