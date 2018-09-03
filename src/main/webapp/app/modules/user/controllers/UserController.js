module.exports = function ($stateParams, ProjectService, AppService, UserService, $uibModal, $state, $scope) {

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
      //A default value is set to cover for old data that do not have an associated project to populate the dropdonw.
      if(!context.profile.owner.project){
        profile.owner.project = {id: 0, name: 'Não'};
      }
    }else{
      $state.go('404');
    }
  });

  $scope.initSelect = function(){
    ProjectService.getProjects().then(function(data){
      if(!data){ data = []; }
      data.unshift({id:0, name:'Não'});
      context.dropDownProjects = data;
    });
  };

  $scope.onProjectSelection = function(){
    if(context.profile.owner.project.id == 0){
      var user = angular.copy(context.profile.owner);
      delete user.project;
      UserService.updateUserProject(user);
    }else {
      UserService.updateUserProject(context.profile.owner);
    }
  };

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
  };

};
