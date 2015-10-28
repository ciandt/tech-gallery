module.exports = function ($window, $rootScope, AuthService) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * Loading state
   * @type {Boolean}
   */
  this._loading = true;

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

  /**
   * User login state
   * @type {Boolean}
   */
  $rootScope.isUserLogged = true;

  /**
   * Ìnitialize AuthService if the API isn't loaded yet
   * @return {Void}
   */
  $window.gapiClientLoaded = function () {
    if (!$rootScope.isApiLoaded) {
      AuthService.init();
    }
  }

  /**
   * Return loading state
   * @return {Boolean}
   */
  this.isLoading = function () {
    return context._loading;
  }

  /**
   * Set loading state
   * @param {Boolean} state The state to be set
   */
  this.setLoading = function (state) {
    context._loading = !!state;
  }
}
