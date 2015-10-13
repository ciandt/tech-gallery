angular.module('techGallery').controller(
  'createTechController',
  function($scope, $timeout, $modal) {
    'use strict';
    //Fill this property with the domain of your choice
    $scope.domain = '@ciandt.com';
    
    $scope.logoutRedirect = function(){
      return jsUtils.logoutRedirect();
    }
    
    $scope.indexPage = function(){
      var indexPage = location.protocol;
      indexPage += '//';
      indexPage += location.hostname;
      return indexPage;
    }

    var alerts = jsUtils.alerts;

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
    	document.getElementById('idimage').addEventListener('change', handleFileSelect, false);
    	$scope.$apply();
    }

    function handleFileSelect(evt) {
        var files = evt.target.files;
        var f = files[0];
        var reader = new FileReader();
        reader.onload = (function(theFile) {
        	return function(e) {
        		$scope.image = e.target.result;
        		document.getElementById('list').innerHTML = ['<img src="', e.target.result,'" title="', theFile.name, '" width="200" />'].join('');
        	};
        })(f);
        reader.readAsDataURL(f);
    }

    
//    function fillTechnology(technology) {
//      $scope.name = technology.name;
//      $scope.description = technology.description;
//      $scope.recommendation = technology.recommendation;
//      $scope.image = technology.image;
//      $scope.website = technology.website;
//    }

    $scope.closeAlert = function() {
      $scope.alert = undefined;
    };

    function setClassElement(id){
      var elementClassIncrease = 'btn GPlusDefault';
      var elementClassDecrease = 'btn GPlusAdded';
      var elementClass = document.getElementById(id).className;
      if (elementClass.indexOf('GPlusAdded') < 0) {
        document.getElementById(id).className = elementClassDecrease;
      } else {
        document.getElementById(id).className = elementClassIncrease;
      }
    }
    
    $scope.cleaTechnology = function(){
    	$scope.name = '';
    	$scope.description = '';
    	$scope.shortDescription = '';
    	$scope.webSite = '';
    	document.getElementById('idimage').value = null;
    	document.getElementById('list').innerHTML = ['<img src="/images/no_image.png" title="Insira uma imagem" width="200" />'].join('');
    }
    
    this.submitForm = function(form){
    	var formData = new FormData(form);
    	$http.post(uploadUrl, formData, {
    				transformRequest: angular.identity,
    				headers: {'Content-Type': undefined}
    	})
    	return false;
    }
  }
);