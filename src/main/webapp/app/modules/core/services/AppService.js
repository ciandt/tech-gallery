module.exports = function ($rootScope) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * Application loading state
   * @type {Boolean}
   */
  this.loading = false;

  /**
   * Application page title
   * @type {String}
   */
  this.pageTitle = 'Index';

  /**
   * Application name
   * @type {String}
   */
  this.appName = 'Tech Gallery';

  /**
   * Update the page <title>. Add the appName at the end.
   *
   * @param {String} title The new page title
   */
  this.setPageTitle = function (title) {
    if (title && title.length) {
      $rootScope.pageTitle = title + ' - ' + context.appName;
    }
  }

  this.setLoading = function (state) {
    if (state) {
      this.loading = !!state;
    }
  }

  /**
   * The page title
   * @type {String}
   */
  this.setPageTitle(this.pageTitle);

  /**
   * Expose application loading state to $rootController
   * @return {Boolean}
   */
  $rootScope.isLoading = this.loading;
}
