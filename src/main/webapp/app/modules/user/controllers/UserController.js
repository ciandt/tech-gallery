module.exports = function ($stateParams, AppService, UserService) {

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
   * The user profile info
   * @type {Object}
   */
  this.profile = UserService.profile;

  // Update the user info based on the URL param
  UserService.updateUserProfile($stateParams.id).then(function (user) {
    context.loading = false;
    AppService.setPageTitle(user.name);
  });

}
