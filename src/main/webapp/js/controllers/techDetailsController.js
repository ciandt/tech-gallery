angular.module('techGallery').controller(
  'techDetailsController',
  function($scope, $timeout, $modal) {
    'use strict';
    
    var featureEnum = {
        ENDORSE: 'ENDORSE',
        COMMENT: 'COMMENT',
        RECOMMEND: 'RECOMMEND'
    };
    
    $scope.currentPage = document.location.href;
    
    $scope.getUsersList = function (value){
      var req = {query:value};
      return gapi.client.rest.usersAutoComplete(req).then(function (data){
        for(var i in data.result.items){
          if(!data.result.items[i].photo){
            data.result.items[i].photo = "/images/default-user-image.jpg";
          }
        }
        return data.result.items;
      });
    }
    
    $scope.idTechnology = jsUtils.getParameterByName('id');
    $scope.loadEndorsements = true;
    $scope.showTechNotExists = false;

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
    
    $scope.redirectUserProfile = function(email) {
      var userId = email.split('@')[0];
      var protocol = location.protocol + '//';
      var host = protocol + location.host;
      var servletRedirect = '/userProfile.html';
      var queryString = '?userId=';
      return host + servletRedirect + queryString + userId;
    };

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
      gapi.client.rest.getLoggedUser().execute(function(data) {
        $scope.loggedUserInformation = data;
        $scope.postGooglePlus = data.postGooglePlusPreference;
      });
      gapi.client.rest.getTechnology(req).execute(function(data) {
    	if(data.code !== undefined && data.code === 404){
    		$scope.showContent = false;
    		$scope.showTechNotExists = true;
    		$scope.$apply();
    		return;
	    }
        gapi.client.rest.getUserSkill(req).execute(function(dataSkill) {
          $scope.rate = dataSkill.value;
          $scope.skillLevel = returnSkillLevel(dataSkill.value);
        });

        fillTechnology(data);
        showEndorsementsByTech();
        loadComments();
        loadRecommends()
        loadNotRecommends();
        $scope.disablePlusOne = false;
        $scope.$apply();
      });
    }

    function fillTechnology(technology) {
      $scope.name = technology.name;
      $scope.id = technology.id;
      $scope.description = technology.description;
      $scope.recommendation = technology.recommendation;
      $scope.justification = technology.recommendationJustification;
      $scope.image = technology.image;
      $scope.website = technology.website;
      $scope.followedByUser = technology.followedByUser;
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
      if($scope.endorsed.email){
        req.endorsed = $scope.endorsed.email;
      }else{
        req.endorsed = $scope.endorsed;
      }
      req.technology = $scope.idTechnology;
      if ($scope.endorsed.email || $scope.endorsed) {
        gapi.client.rest.addEndorsement(req).execute(function(data) {
          
          if($scope.postGooglePlus && !data.hasOwnProperty('error')){
            var req = {
                feature : featureEnum.ENDORSE,
                currentUserMail : data.endorser.email,
                endorsedMail : data.endorsed.email,
                technologyName : data.technology.name,
                appLink : $scope.currentPage
            }
            gapi.client.rest.postComment(req).execute();
          }
          
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
            if(fullResponse) {
            	var endorsersFiltered = fullResponse.slice(0,5);
            	response[i].endorsersFiltered = endorsersFiltered;
            	if(!response[i].endorsed.photo) {
            		response[i].endorsed.photo = "/images/default-user-image.jpg";
            	}
            	response[i].endorsers = setPlusOneClass(response[i].endorsers);
            }
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
          ga('send', 'event', 'TechGalleryEvents', 'skill_add', $scope.name);
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
    			var req = {
    					technologyId : $scope.idTechnology,
    					comment : $scope.comment
    			};
    			gapi.client.rest.addComment(req).execute(function(data) {
    				$scope.processingComment = true;
    				callBackLoaded();
    				$scope.comment = '';
    				if($scope.postGooglePlus && !data.hasOwnProperty('error')){
		              var req = {
        	          		feature : featureEnum.COMMENT,
            	      		currentUserMail : data.author.email,
	                	  	technologyName : $scope.name,
					  		comment: data.comment,
		 					appLink: $scope.currentPage
        			      }
		              gapi.client.rest.postComment(req).execute();
		            }
    			});
    			ga('send', 'event', 'TechGalleryEvents', 'comment_add', $scope.name);
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
    				$scope.score = undefined;
    				$scope.setClassThumbs('');
    				if($scope.postGooglePlus && !data.hasOwnProperty('error')){
		              var req = {
        		          feature : featureEnum.RECOMMEND,
                		  score : data.score,
		                  currentUserMail : data.recommender.email,
        		          technologyName : data.technology.name,
                		  appLink: $scope.currentPage
		              }
        		      gapi.client.rest.postComment(req).execute();
		            }
    			});
    			ga('send', 'event', 'TechGalleryEvents', 'recommendation_add', $scope.name);
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
    		$scope.techCommentsFull = data.comments;
    		$scope.processingComment = false;
    		$scope.$apply();
    	});
    }
    
    $scope.filterRecommenders = function(){
    	if($scope.up > 0 && $scope.techCommentsFull){
    		$scope.techComments = [];
    		for (var i = 0; i < $scope.techCommentsFull.length; i++) {
    			if($scope.techCommentsFull[i].recommendationScore != null && $scope.techCommentsFull[i].recommendationScore){
    				$scope.techComments.push($scope.techCommentsFull[i]);
    			}
    		}
    		document.getElementById("txta_comment").focus();
    	}else{
    		$scope.alertComment = true;
    		$scope.alertMsgComment = 'Ninguém recomendou esta tecnologia ainda.';
    	}
    }
    
    $scope.filterNoRecommenders = function(){
    	if($scope.down > 0 && $scope.techCommentsFull){
    		$scope.techComments = [];
    		for (var i = 0; i < $scope.techCommentsFull.length; i++) {
    			if($scope.techCommentsFull[i].recommendationScore != null && !$scope.techCommentsFull[i].recommendationScore){
    				$scope.techComments.push($scope.techCommentsFull[i]);
    			}
    		}
    		document.getElementById("txta_comment").focus();
    	}else{
    		$scope.alertComment = true;
    		$scope.alertMsgComment = 'Ninguém não recomendou esta tecnologia ainda.';
    	}
    }
    
    $scope.removeFilter = function(){
    	if($scope.techCommentsFull){
    		$scope.techComments = [];
    		$scope.techComments = $scope.techCommentsFull;
    	}
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
    
    function loadRecommends() {
    	var req = {id : $scope.idTechnology};
    	gapi.client.rest.getRecommendationsUp(req).execute(function(data){
    		$scope.up = 0;
    		if(data.items !== undefined){
    			$scope.up = data.items.length;
    		}
    		$scope.$apply();
    	});
    }
    
    function loadNotRecommends() {
    	var req = {id : $scope.idTechnology};
    	gapi.client.rest.getRecommendationsDown(req).execute(function(data){
    		$scope.down = 0;
    		if(data.items !== undefined){
    			$scope.down = data.items.length;
    		}
    		$scope.$apply();
    	});
    }
    
    $scope.deleteComment = function(id) {
    	if(confirm('Você realmente quer apagar o comentário?')) {
    		var reqRecommend = null;
    		for (var i = 0; i < $scope.techComments.length; i++) {
    			if($scope.techComments[i].id != null && $scope.techComments[i].id === id){
    				if($scope.techComments[i].recommendationScore != null){
    					reqRecommend = {
    							    recommendId : $scope.techComments[i].recommendationId,
    							    commentId: id
    							    };
    				}
    				break;
    			}
    		}
    		if(reqRecommend){
    			gapi.client.rest.deleteCommentAndRecommendation(reqRecommend).execute(function(data){
    				loadComments();
    				loadRecommends();
    				loadNotRecommends();
    			});
    		}else{
    			var req = {commentId: id};
    			gapi.client.rest.deleteComment(req).execute(function(data){
    				loadComments();
    			});
    		}
    	}
    };
    
    $scope.setFollowedClass = function(isFollowedByUser){
      return jsUtils.setFollowedClass(isFollowedByUser);
    }
    
    $scope.followTechnology = function(){
      var req = {technologyId: $scope.idTechnology}
      gapi.client.rest.followTechnology(req).execute(function(data){
        if(!data.hasOwnProperty('error')){
            var elementId = 'btn-follow-' + data.id;
            changeFollowedClass(elementId);
        }
      });
    }
    
    function changeFollowedClass(elementId){
      var element = document.getElementById(elementId)
      var oldClass = element.className;
      if(oldClass.indexOf('btn-primary') > 0){
        element.className = 'btn btn-danger';
      }else{
        element.className = 'btn btn-primary';
      }
    }
    
    $scope.generateFollowId = function(){
      return 'btn-follow-' + $scope.idTechnology;
    }
    
    $scope.changePreference = function(){
      var oldValue = !$scope.postGooglePlus;
      var req = {postGooglePlusPreference: $scope.postGooglePlus}
      gapi.client.rest.saveUserPreference(req).execute(function(data){
        if(data.hasOwnProperty('error')){
          $scope.postGooglePlus = oldValue;
        }
      });
    }
    
    $scope.editTechnology = function(){
    	window.location = $scope.redirectUrl($scope.id, '/createTech.html');
    }
    
    $scope.redirectUrl = function(techId, servlet) {
        var protocol = location.protocol + '//';
        var host = protocol + location.host;
        var servletRedirect = servlet;
        var queryString = '?id=';
        return host + servletRedirect + queryString + techId;
      };
    
  }
);