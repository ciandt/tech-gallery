angular.module('techGallery').controller(
  'techListController',
  function($scope, $location, $timeout) {
    'use strict';

    $scope.domainError = false;
    $scope.userLogged = false;
//    $scope.selectedRecommendation = 'Selecione';

    $scope.indexPage = function(){
      var indexPage = location.protocol;
      indexPage += '//';
      indexPage += location.hostname;
      return indexPage;
    }

    $scope.logoutRedirect = function(){
      return jsUtils.logoutRedirect();
    }

    var executeAuth = function(immediate) {
      $timeout(function() {
        jsUtils.checkAuth(successFunction, immediate);
      }, 200);
    }

    $scope.login = function() {
      executeAuth(false);
    }

    var successFunction = function(data) {
      if(data !== false && !data.error){
        $scope.showLogin = false;
        $scope.showLoading = true;
        $scope.domainError = undefined;
        $scope.userLogged = true;
        $scope.$apply();
        getTechList();
      }else{
        if(data.error){
          $scope.domainError = data.message;
        }
        $scope.userLogged = false;
        $scope.showLogin = true;
        $scope.showLoading = false;
        $scope.$apply();
      }
    }

    executeAuth(true);

    $scope.redirectUrl = function(techId) {
      var protocol = location.protocol + '//';
      var host = protocol + location.host;
      var path = location.pathname;
      if (path === '/') {
        path = '';
      }
      var servletRedirect = '/viewTech';
      var queryString = '?id=';
      return host + path + servletRedirect + queryString + techId;
    };

    function getTechList() {
      var protocol = location.protocol + '//';
      var host = location.host;
      var complement = '/_ah/api/';
      var rootUrl = protocol + host + complement;
      gapi.client.load('rest', 'v1', callBackLoaded, rootUrl);
    }

    function callBackLoaded() {
      gapi.client.rest.getTechnologies().execute(function(data) {
        gapi.client.rest.handleLogin().execute();
        gapi.client.rest.getOrderOptions().execute(function(data) {
          $scope.orderOptions = data.items;
        });
        gapi.client.rest.getRecommendations().execute(function(data){
          $scope.dropDownRecommendation = data.items;
          $scope.$apply();
        });
        $scope.techList = data.technologies;
        $scope.showLoading = false;
        $scope.$apply();
      });
    }

    $scope.selectOrderOption = function(orderOption) {
      $scope.selectedOrderOption = orderOption;
    }

    $scope.searchTechnology = function (dateFilter){
      if($scope.textSearch || $scope.selectedOrderOption || $scope.selectedRecommendation || dateFilter >= 0){
        $scope.techList = '';
        $scope.showLoading = true;
        var req = {
            titleContains: $scope.textSearch,
            shortDescriptionContains: $scope.textSearch,
            orderOptionIs: $scope.selectedOrderOption,
            dateFilter : dateFilter,
            recommendationIs: $scope.selectedRecommendation
        }
        gapi.client.rest.findByFilter(req).execute(function(data){
          $scope.techList = data.technologies;
          $scope.showLoading = false;
          $scope.$apply();
        });
      }
    }

    $scope.searchClear = function (){
      $scope.techList = '';
      $scope.textSearch = '';
      $scope.selectedOrderOption = undefined;
      $scope.selectedRecommendation = undefined;
      $scope.showLoading = true;
      callBackLoaded();
    }

    $scope.selectRecommendation = function(selected){
      $scope.selectedRecommendation = selected;
    };

    $scope.selectOrderOption = function(selected){
      $scope.selectedOrderOption = selected;
    };

    $scope.deleteTechnology = function(technologyId){
      if(confirm('Você realmente quer apagar a tecnologia?')) {
        var req = {technologyId: technologyId};
        gapi.client.rest.deleteTechnology(req).execute(function(data){
          if(!data.hasOwnProperty('error')){
            callBackLoaded();
          }
        });
      }
    }
  }
);
