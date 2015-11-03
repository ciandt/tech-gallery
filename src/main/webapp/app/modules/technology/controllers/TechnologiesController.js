module.exports = function ($scope, $rootScope, AppService, TechnologyService) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * Loading state
   * @type {Boolean}
   */
  this.loading = false;

  /**
   * Page title
   */
  AppService.setPageTitle('Tecnologias');

  /**
   * List of technologies
   * @type {Array}
   */
  this.items = TechnologyService.getTechnologies();
}
