module.exports = function ($rootScope, $stateParams, AppService, TechnologyService) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * Loading state
   * @type {Boolean}
   */
  this.loading = true;

  /**
   * Technology details
   * @type {Object}
   */
  this.item = {};

  // Load techonlogy based on URL param
  TechnologyService.getTechnology($stateParams.id, function (technology) {
    AppService.setPageTitle(technology.name);
    context.item = technology;
    context.loading = false;
  });

  this.ratings = TechnologyService.getRatings();

  this.rating = 0;

  this.setSkill = function (technology, rating) {
    context.rating = rating;
  }
}
