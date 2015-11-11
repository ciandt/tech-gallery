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
      controller : 'AppController as app',
      templateUrl: 'app/templates/default.html',
      resolve: {
        loadEndpoints: function ($rootScope, API, $q, AppService) {
          $rootScope.apiLoaded = false;
          var deferred = $q.defer();
          var gapiInterval = window.setInterval(function() {
            if ($rootScope.apiLoaded) {
              window.clearInterval(gapiInterval);
              return;
            }

            gapi.client.load(API.NAME, API.VERSION, function (data) {
              $rootScope.apiLoaded = true;
              AppService.setLoading(false);
              deferred.resolve();
            }, API.URL);
          }, 200);

          return deferred.promise;
        }
      }
    })
    .state('404', {
      url: '/404',
      controller : function (AppService) {
        AppService.setPageTitle('Página não encontrada');
      },
      templateUrl: 'app/templates/404.html'
    });
};
