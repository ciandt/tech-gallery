module.exports = function ($rootScope) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  this._loading = false;
  this._pageTitle = 'Index';
  this._appName = 'Tech Gallery';

  this.getPageTitle = function () {
    return context._pageTitle + ' - ' + context._appName;
  }

  this.setPageTitle = function (title) {
    if (title.length) {
      context._pageTitle = title;
    }
  }

  this.isLoading = function () {
    return this._loading;
  }

  this.setLoading = function (state) {
    if (state) {
      this._loading = !!state;
    }
  }
}
