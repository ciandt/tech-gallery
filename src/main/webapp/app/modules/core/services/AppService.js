module.exports = function ($rootScope, $location, Analytics) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * Application loading state
   * @type {Boolean}
   */
  $rootScope.loading = true;

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

  this.alertBotton = {};

  /**
   * Set app-wide alert message
   * @param {String} message The alert message
   * @param {String} type    The alert type
   * @see https://angular-ui.github.io/bootstrap/#/alert
   */
  this.setAlert = function (message, type) {
    fillAlert(message, type, this.alert);
  }

  /**
   * Remove app-wide alert messafge
   * @return {Void}
   */
  this.closeAlert = function () {
    angular.copy({}, this.alert)
  }

  this.setAlertBotton = function (message, type) {
    fillAlert(message, type, this.alertBotton);
  }

  /**
   * Remove app-wide alert messafge
   * @return {Void}
   */
  this.closeAlertBotton = function () {
    angular.copy({}, this.alertBotton)
  }

  function fillAlert(message, type, alert) {
    var type = (type == 'error') ? 'danger' : type;
    angular.copy({
      message: message,
      type:  type || 'warning'
    }, alert);
  }

  /**
   * Listener to close alert messages.
   * @return {Void}
   */
  $rootScope.$on('$locationChangeSuccess', function(event, newUrl, oldUrl) {
    if(newUrl.indexOf('technologies/') >= 0){
      Analytics.trackTechnologyAcess(newUrl.substring(newUrl.indexOf('technologies')+13));
    }
    if(!$rootScope.technologySaved && (oldUrl.indexOf('/new') < 0 || oldUrl.indexOf('/edit') < 0)){
      context.closeAlert();
    }
    $rootScope.technologySaved = undefined;
    context.closeAlertBotton();
  });

  // this.setAlert('Teste', 'error');

  /**
   * Update the page <title>. Add the appName at the end.
   * @param {String} title The new page title
   */
  this.setPageTitle = function (title) {
    if (title && title.length) {
      $rootScope.pageTitle = title + ' - ' + context.appName;
    }
  }

  /**
   * Set application loading state
   * @param {Boolean} state The loading state true|false
   */
  this.setLoading = function (state) {
    $rootScope.loading = !!state;
  }

  this.setPageTitle('Index');
}
