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

  //A default value is set to cover for old data that do not have an associated project to populate the dropdonw.
  if(!context.project){
    profile.owner.project = {id: 0, name: "Não"};
  }

  $scope.initSelect = function(){
    console.log("init");
    console.log(context);
    ProjectService.getProjects().then(function(data){
      console.log(data);
      if(!data){ data = [] };
      data.unshift({id:0, name:"Não"});
      context.dropDownProjects = data;
    });
  }

  $scope.onProjectSelection = function(){
    console.log(context.profile.owner);
    UserService.updateUserProject(context.profile.owner);
  }

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
