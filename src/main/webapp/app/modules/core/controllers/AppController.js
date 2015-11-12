module.exports = function ($scope, $rootScope, AppService, AuthService, UserService) {

  /**
   * Object context
   * @type {Object}
   */
   var context = this;

  /**
   * Bind app.alert to the Service model
   * @type {Object}
   */
  this.alert = AppService.alert;

  this.alertBotton = AppService.alertBotton;

  /**
   * Bind the alert close method to the Service
   * @type {Function}
   */
  this.closeAlert = AppService.closeAlert;

  this.closeAlertBotton = AppService.closeAlertBotton;

   $rootScope.$watch('apiLoaded', function(newValue, oldValue) {
    if(newValue && !$rootScope.isUserLogged){
      AuthService.checkAuth(afterLogin, true)
    }
  });

  function checkAuth(immediate){
    AuthService.checkAuth(afterLogin, immediate);
  }

  var afterLogin = function(data){
    if(data){
      $rootScope.isUserLogged = true;
      UserService.getUserInformations();
      $scope.$apply();
    }else{
      $rootScope.isUserLogged = false;
      $scope.$apply();
    }
  }

  this.login = function(){
    checkAuth(false);
  }

}
