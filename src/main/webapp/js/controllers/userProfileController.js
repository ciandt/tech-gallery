angular.module('techGallery').controller(
  'userProfileController',
  function($scope, $timeout) {
    'use strict';
    $scope.userId = jsUtils.getParameterByName('userId');
    $scope.defaultUserPhoto = "/images/default-user-image.jpg";
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
         email : userMail
      };
      gapi.client.rest.profile.get(req).execute(function(data) {
        var response = data;
        if(response){
          if(response.positiveRecItems){
            $scope.completePositiveItems = response.positiveRecItems;
            response.positiveRecItems = response.positiveRecItems.slice(0,3);
          }
          if(response.negativeRecItems){
            $scope.completeNegativeItems = response.negativeRecItems;
            response.negativeRecItems = response.negativeRecItems.slice(0,3);
          }
          if(response.otherItems ){
            $scope.completeOtherItems = response.otherItems;
            response.otherItems = response.otherItems.slice(0,3);
          }
          
          $scope.userProfile = response;
          $scope.showContent = true;
          $scope.$apply();
        }else{
          $scope.showContent = false;
          $scope.$apply();
        }
      });
    }
    
    $scope.showAllItems = function(type){
      if(type=="positive"){
        $scope.userProfile.positiveRecItems = $scope.completePositiveItems;
      }else if(type == "negative"){
        $scope.userProfile.negativeRecItems = $scope.completeNegativeItems;
      }else if(type=="other"){
        $scope.userProfile.otherItems = $scope.completeOtherItems;
      }
    }
    
    $scope.showLessItems = function(type){
      if(type=="positive"){
        $scope.userProfile.positiveRecItems = $scope.userProfile.positiveRecItems.slice(0,3);
      }else if(type == "negative"){
        $scope.userProfile.negativeRecItems = $scope.userProfile.negativeRecItems.slice(0,3);
      }else if(type=="other"){
        $scope.userProfile.otherItems = $scope.userProfile.otherItems.slice(0,3);
      }
    }
});