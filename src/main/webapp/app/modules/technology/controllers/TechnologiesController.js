module.exports = function ($scope, $rootScope, TechnologyService) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * Loading state
   * @type {Boolean}
   */
  this._loading = false;

  /**
   * Page title
   * @type {String}
   */
  $rootScope.pageTitle = 'Tecnologias';

  /**
   * List of technologies
   * @type {Array}
   */
  this.items = TechnologyService.getTechnologies();

  /**
   * Return loading state
   * @return {Boolean}
   */
  this.isLoading = function () {
    return context._loading;
  }
}
