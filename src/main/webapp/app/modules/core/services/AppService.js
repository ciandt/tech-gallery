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
  this.loading = true;

  /**
   * Application name
   * @type {String}
   */
  this.appName = 'Tech Gallery';

  /**
   * Application-wide alert message
   * @type {Object}
   */
  this.alert = {};

  /**
   * Set app-wide alert message
   * @param {String} message The alert message
   * @param {String} type    The alert type
   * @see https://angular-ui.github.io/bootstrap/#/alert
   */
  this.setAlert = function (message, type) {
    var type = (type == 'error') ? 'danger' : type;
    angular.copy({
      message: message,
      type:  type || 'warning'
    }, this.alert);
  }

  /**
   * Remove app-wide alert messafge
   * @return {Void}
   */
  this.closeAlert = function () {
    angular.copy({}, this.alert)
  }

  /**
   * Listener to close alert messages.
   * @return {Void}
   */
  $rootScope.$on('$locationChangeSuccess', function(next, current) {
    context.closeAlert();
  });

  // this.setAlert('Teste', 'error');

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
    context.loading = !!state;
    console.log('set loading state to:', context.loading, !!state, state);
    // $rootScope.$apply();
  }

  this.setPageTitle('Index');
}
