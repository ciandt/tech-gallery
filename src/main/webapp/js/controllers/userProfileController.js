angular.module('techGallery').controller(
  'userProfileController',
  function($scope, $timeout) {
    'use strict';
    $scope.userId = jsUtils.getParameterByName('userId');
    
    $scope.logoutRedirect = function(){
      return jsUtils.logoutRedirect();
    }
    
    $scope.indexPage = function(){
      var indexPage = location.protocol;
      indexPage += '//';
      indexPage += location.hostname;
      return indexPage;
    }

    var successFunction = function(data) {
      if(data!==false && !data.error){
        $scope.showContent = true;
        $scope.showLogin = false;
        $scope.domainError = undefined;
        $scope.userLogged = true;
        var protocol = location.protocol + '//';
        var host = location.host;
        var complement = '/_ah/api/';
        var rootUrl = protocol + host + complement;
        $scope.userEmail = data.userEmail;
        gapi.client.load('rest', 'v1', callBackLoaded, rootUrl);
      }else{
        if(data.error){
          $scope.domainError = data.message;
        }
        $scope.userLogged = false;
        $scope.showContent = false;
        $scope.showLogin = true;
        $scope.$apply();
      }
    }
    
    function checkLogin(immediate){
      $timeout(function() {
        jsUtils.checkAuth(successFunction, immediate);
      }, 200);
    }
    
    checkLogin(true);

    $scope.login = function(){
      checkLogin(false);
    };

    function callBackLoaded() {
      var userMail = $scope.userId + "@ciandt.com";
      var req = {
        id : userMail
      };
      var response = jsUtils.mockUserProfile(userMail);
      $scope.userProfile = response;
      $scope.$apply();
//      gapi.client.rest.profile.get(req).execute(function(data) {
//        $scope.userProfile = data;
//      });
    }
    
    $scope.getTechnologyImage = function (techName){
      var nameNormalized = techName.toString().toLowerCase().replace(/\s+/g, '');
      return "https://storage.googleapis.com/tech-gallery-assets/imagesLogo/"+nameNormalized+".png"
    }
  }
);