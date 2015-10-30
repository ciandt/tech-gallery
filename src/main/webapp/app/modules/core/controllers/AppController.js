module.exports = function ($window, $rootScope, AppService, AuthService) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * The page title
   * @type {String}
   */
  $rootScope.pageTitle = AppService.getPageTitle();

  /**
   * gapi api loaded state
   * @type {Boolean}
   */
  $rootScope.isApiLoaded = false;

  /**
   * User login state
   * @type {Boolean}
   */
  $rootScope.isUserLogged = AuthService.isLogged();

  /**
   * Return loading state
   * @return {Boolean}
   */
  $rootScope.isLoading = AppService.isLoading();

  /**
   * Set loading state
   * @param {Boolean} state The state to be set
   */
  $rootScope.setLoading = AppService.setLoading;
}
