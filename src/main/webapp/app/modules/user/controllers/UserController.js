module.exports = function ($stateParams, AppService, UserService, $uibModal) {

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
    context.profile = user;
    context.loading = false;
    AppService.setPageTitle(user.name);
  });

  this.openCommentsFor = function (technology) {
    var modalInstance = $uibModal.open({
      templateUrl: 'comments.html',
      controller: function ($scope) {
        $scope.user = {};
        $scope.user.name = context.profile.name;
        $scope.user.image = context.profile.image;
        $scope.technology = technology;
        $scope.close = $scope.$close;
      },
      size: 'lg'
    });
  }

}
