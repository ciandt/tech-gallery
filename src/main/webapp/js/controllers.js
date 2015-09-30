/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

angular.module('techGallery').controller(
  'techListController',
  function($scope, $location, $timeout) {
    'use strict';

    $scope.domainError = false;
    $scope.userLogged = false;
    
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
        $scope.techList = data.technologies;
        $scope.showLoading = false;
        $scope.$apply();
      });
    }
    
    $scope.selectOrderOption = function(orderOption) {
    	$scope.selectedOrderOption = orderOption;
    }
    
    $scope.searchTechnology = function (){
      if($scope.textSearch || $scope.selectedOrderOption){
        $scope.techList = '';
        $scope.showLoading = true;
        var req = {
            titleContains: $scope.textSearch,
            shortDescriptionContains: $scope.textSearch,
            orderOption: $scope.selectedOrderOption
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
      $scope.selectedOrderOption = '';
      $scope.showLoading = true;
      callBackLoaded();
    }
  }
);

angular.module('techGallery').controller(
  'techDetailsController',
  function($scope, $timeout, $modal) {
    'use strict';
    $scope.idTechnology = jsUtils.getParameterByName('id');
    $scope.loadEndorsements = true;

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
      var idTech = $scope.idTechnology;
      var req = {
        id : idTech
      };
      gapi.client.rest.getTechnology(req).execute(function(data) {
        gapi.client.rest.getUserSkill(req).execute(function(dataSkill) {
          $scope.rate = dataSkill.value;
          $scope.skillLevel = returnSkillLevel(dataSkill.value);
        });

        fillTechnology(data);
        showEndorsementsByTech();
        loadComments();
        showAllRecommenders();
        showAllNotRecommenders();
        $scope.disablePlusOne = false;
        $scope.$apply();
      });
    }

    function fillTechnology(technology) {
      $scope.name = technology.name;
      $scope.description = technology.description;
      $scope.recommendation = technology.recommendation;
      $scope.image = technology.image;
      $scope.website = technology.website;
    }

    $scope.closeAlert = function() {
      $scope.alert = undefined;
    };

    /**
     * Begin of the Recommend Features
     */
    $scope.endorse = function(alertUser) {
      $scope.processEndorse = true;
      var req = {};
      req.endorsed = $scope.endorsed;
      req.technology = $scope.idTechnology;
      if ($scope.endorsed) {
        gapi.client.rest.addEndorsement(req).execute(function(data) {
          if(alertUser){
            var alert;
            if (data.hasOwnProperty('error')) {
              alert = alerts.failure;
              alert.msg = data.error.message;
            }else{
              alert = alerts.success;
            }
            $scope.alert = alert;
          }
          $scope.endorsed = '';
          callBackLoaded();
        });
      }
      $scope.processEndorse = false;
      $scope.$apply();
    };

    /**
     * Begin of Show Endorsement Features
     */
    $scope.showAllEndorsers = function(endorsers) {
      return (endorsers.length > 0);
    };

    function showEndorsementsByTech() {
      var idTech = $scope.idTechnology;
      var req = {
        id : idTech
      };
      gapi.client.rest.getEndorsementsByTech(req).execute(function(data) {
        if(data.result && data.result.endorsements){
          var response = data.result.endorsements;
          for(var i in response){
            var fullResponse = response[i].endorsers;
            var endorsersFiltered = fullResponse.slice(0,5);
            response[i].endorsersFiltered = endorsersFiltered;
            if(!response[i].endorsed.photo) {
              response[i].endorsed.photo = "/images/default-user-image.jpg";
            }
            response[i].endorsers = setPlusOneClass(response[i].endorsers);
          }
        }
        $scope.showEndorsementResponse = response;
        $scope.processingEndorse = false;
        $scope.loadEndorsements = false;
        $scope.$apply();
      });
    }

    $scope.open = function(endorsers, size) {
      var modalInstance = $modal.open({
        animation : true,
        templateUrl : '/showEndorsementModal.html',
        controller : 'modalController',
        size : size,
        resolve : {
          endorsers : function() {
            return endorsers;
          }
        }
      });
    };


    /*
     *
     * Begin of +1 features
     *
     */
    function setPlusOneClass(endorsers){
      for(var i in endorsers){
        if(endorsers[i].email == $scope.userEmail){
          endorsers.plusOneClass = 'btn GPlusAdded';
          return endorsers;
        }
      }
      endorsers.plusOneClass = 'btn GPlusDefault';
      return endorsers;
    };
    
    $scope.setToolTipPlusOne = function(index, email){
      var id = $scope.generateId(index, email);
      var element = document.getElementById(id);
      if(element && element.className.indexOf('GPlusDefault') !== -1){
        return "+1 indicação para o usuário";
      }
      return "Remover sua indicação +1 para o usuário"
    };

    $scope.showSelfInformations = function(email){
      if($scope.userEmail == email){
        return true;
      }

      return false;
    };

    $scope.generateId = function(index, email) {
      return 'plusOne' + index + email;
    };

    $scope.addEndorse = function(endorsed, id) {
      $scope.disablePlusOne = true;
      $scope.processingEndorse = true;
      var completeEmail = endorsed.email;
      completeEmail = completeEmail.split('@');
      var email = completeEmail[0];
      var req = {};
      req.endorsed = email;
      req.technology = $scope.idTechnology;
      gapi.client.rest.addEndorsementPlusOne(req).execute(function(data){
        callBackLoaded();
      });
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

    /**
     * Begin of inform skill features
     */

    //Fill user's rate and skill in that tech
    $scope.rate = 0;
    $scope.skillLevel = undefined;

    $scope.max = 5;
    $scope.isReadonly = false;

    $scope.hoveringOver = function(value) {
      $scope.overStar = value;
      $scope.percent = 100 * (value / $scope.max);
      $scope.skillLbl = returnSkillLevel(value);
    };

    $scope.$watch('rate', function(newValue, oldValue) {
      if (newValue !== oldValue) {
        $scope.skillLevel = returnSkillLevel(newValue);
        //Make API call to save the skill

        var idTech = $scope.idTechnology;
        var req = {
          technology : idTech,
          value : newValue
        };
        gapi.client.rest.addSkill(req).execute(function(data) {
          $scope.processingEndorse = true;
          callBackLoaded();
        });

      }
    });

    function returnSkillLevel(rate) {
      switch (rate) {
      case 1:
        return 'Newbie';
      case 2:
        return 'Initiate';
      case 3:
        return 'Padawan';
      case 4:
        return 'Knight';
      case 5:
        return 'Jedi';
      default:
        return null;
      }
    }
    
    /**
     * Begin of create comments features
     */
    $scope.clearComment = function(){
    	$scope.comment = '';
    }
    
    $scope.addComment = function(){
    	if($scope.comment && $scope.comment.trim().length <= 500){
    		if($scope.score == undefined){
    			//Call API to add a comment
    			var req = {
    					technologyId : $scope.idTechnology,
    					comment : $scope.comment
    			};
    			gapi.client.rest.addComment(req).execute(function(data) {
    				$scope.processingComment = true;
    				callBackLoaded();
    				$scope.comment = '';
    			});
    		}else {
    			//Call API to add a comment and a recommendation
    			var req = {
    					technology : {id : $scope.idTechnology},
    					comment : {comment : $scope.comment},
    					recommendation : {score : $scope.score}
    			};
    			gapi.client.rest.addRecommendationComment(req).execute(function(data) {
    				$scope.processingComment = true;
    				callBackLoaded();
    				$scope.comment = '';
    			});
    		}
    	}else{
    		if($scope.score !== undefined){
    			$scope.alertComment = true;
    			$scope.alertMsgComment = 'Você deve informar um comentário sobre sua recomendação.';
    		}
    	}
    }
    
    $scope.closeAlertComment = function() {
	    $scope.alertComment = undefined;
    };
    
    /**
     * Begin of show comments features
     */
//    $scope.techComments=jsUtils.mockTechComment();
    function loadComments() {
    	var req = {technologyId: $scope.idTechnology};
    	gapi.client.rest.getCommentsByTech(req).execute(function(data){
    		$scope.techComments = data.comments;
    		$scope.processingComment = false;
    		$scope.$apply();
    	});
    }
    
    $scope.changeThumbs = function(thumbs){
    	if(thumbs == 'up'){
    		if($scope.score == undefined || $scope.score == false){
    			$scope.score = true;
    			$scope.recommend_message = true;
    		}else {
    			$scope.score = undefined;
    			$scope.recommend_message = false;
    		}
    	}else if(thumbs == 'down'){
    		if($scope.score == undefined || $scope.score == true){
    			$scope.score = false;
    			$scope.recommend_message = true;
    		}else {
    			$scope.score = undefined;
    			$scope.recommend_message = false;
    		}
    	}
    	document.getElementById("txta_comment").focus();
    }
    
    $scope.setClassThumbs = function(thumbs){
    	if($scope.score == undefined){
    		return '';
    	}else if($scope.score == true && thumbs == 'up'){
    		return 'selectedThumbUp';
    	}else if($scope.score == false && thumbs == 'down'){
    		return 'selectedThumbDown';
    	}
    }
    
    $scope.showAllRecommenders = function(){
    	var req = {id : $scope.idTechnology};
    	gapi.client.rest.getRecommendationsUp(req).execute(function(data){
    		$scope.up = 0;
    		if(data.items !== undefined){
    			$scope.up = data.items.length;
    		}
    	});
    }
    
    $scope.showAllNotRecommenders = function(){
    	var req = {id : $scope.idTechnology};
    	gapi.client.rest.getRecommendationsDown(req).execute(function(data){
    		$scope.down = 0;
    		if(data.items !== undefined){
    			$scope.down = data.items.length;
    		}
    	});
    }
    
    $scope.deleteComment = function(id) {
    	if(confirm('Você realmente quer apagar o comentário?')) {
    		var req = {commentId: id};
    		gapi.client.rest.deleteComment(req).execute(function(data){
    			loadComments();
    		});
    	}
    };
  }
);

angular.module('techGallery').controller(
  'modalController',
  function($scope, $modalInstance, endorsers) {
    'use strict';

    $scope.endorsers = endorsers;

    $scope.ok = function() {
      $modalInstance.close();
    };
  }
);
