module.exports = function(
  $stateProvider,
  $urlRouterProvider,
  $urlMatcherFactoryProvider
) {
  // Disable ui-router strict mode
  $urlMatcherFactoryProvider.strictMode(false);

  // Render 404 when no routes are matched
  $urlRouterProvider.otherwise('/404');

  $stateProvider
    .state('root', {
      abstract: true,
      templateUrl: 'app/templates/default.html'
    })
    .state('404', {
      url: '/404',
      templateUrl: 'app/templates/404.html'
    });
};
