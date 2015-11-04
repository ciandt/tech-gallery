module.exports = function ($rootScope, AppService, TechnologyService) {

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

  AppService.setPageTitle('Adicionar nova tecnologia');
}
