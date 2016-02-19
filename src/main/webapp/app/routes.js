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
        },
        loadAnalytics : function ($q) {
          var deferred = $q.defer();

          (function(i,s,o,g,r,a,m){
            i['GoogleAnalyticsObject'] = r;
            i[r] = i[r] || function(){
              (i[r].q = i[r].q || []).push(arguments)
            },
            i[r].l = 1*new Date();
            a = s.createElement(o),
            m = s.getElementsByTagName(o)[0];
            a.async = 1;
            a.src = g;
            m.parentNode.insertBefore(a,m);
            deferred.resolve();
          })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

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
