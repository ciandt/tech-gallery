module.exports = function ($stateParams, AppService, UserService, $uibModal, $state) {

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
  UserService.updateUserProfile($stateParams.id).then(function (profile) {
    if(!profile.hasOwnProperty('error')){
      context.profile = profile;
      context.loading = false;
      AppService.setPageTitle(profile.name);
    }else{
      $state.go('404');
    }
  });

  this.openCommentsFor = function (technology) {
    var modalInstance = $uibModal.open({
      templateUrl: 'comments.html',
      controller: function ($scope) {
        $scope.user = {};
        $scope.user.name = context.profile.owner.name;
        $scope.user.image = context.profile.owner.photo;
        $scope.technology = technology;
        $scope.close = $scope.$close;
      },
      size: 'lg'
    });
  }

}
