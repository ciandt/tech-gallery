module.exports = function ($rootScope, TechnologyService) {

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
   * Page title
   * @type {String}
   */
  $rootScope.pageTitle = 'Technologies';

  /**
   * List of technologies
   * @type {Array}
   */
  this.items = [];

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
