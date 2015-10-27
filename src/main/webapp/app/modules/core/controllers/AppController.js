module.exports = function ($window, $rootScope, AuthService) {
  var context = this;

  /**
   * The app name
   * @type {String}
   */
  $rootScope.appName = 'Tech Gallery';

  /**
   * The page title
   * @type {String}
   */
  $rootScope.pageTitle = 'Index';

  /**
   * gapi api loaded state
   * @type {Boolean}
   */
  $rootScope.isApiLoaded = false;

  $window.gapiClientLoaded = function () {
    if (!$rootScope.isApiLoaded) {
      AuthService.init();
    }
  }
}
