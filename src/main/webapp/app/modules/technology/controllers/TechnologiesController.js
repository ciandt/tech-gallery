module.exports = function ($scope, $rootScope, AppService, TechnologyService, UserService) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * List of technologies
   * @type {Array}
   */
  TechnologyService.getTechnologies().then(function(data){
    context.items = data;
  });

  this.logOutUser = function(){
    return UserService.logOutUser();
  }

  this.searchTechnology = function (dateFilter){
    if($scope.textSearch || $scope.selectedOrderOption || $scope.selectedRecommendation || dateFilter >= 0){
      context.items = '';
      var req = {
          titleContains: $scope.textSearch,
          shortDescriptionContains: $scope.textSearch,
          orderOptionIs: $scope.selectedOrderOption,
          dateFilter : dateFilter,
          recommendationIs: $scope.selectedRecommendation
      }
      TechnologyService.searchTechnologies.then(function(foundTechnologies){
        context.items = foundTechnologies;
        $scope.$apply();
      });
    }
  }

  /**
   * Loading state
   * @type {Boolean}
   */
  this.loading = false;

  /**
   * Page title
   */
  AppService.setPageTitle('Tecnologias');
}
