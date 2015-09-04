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

'use strict';

angular.module('techGallery').controller('techListController',
    function($scope, $location, $timeout) {

      $scope.showLogin = true;

      var executeAuth = function() {
        $timeout(function() {
          jsUtils.checkAuth(successFunction);
        }, 200);
      }

      $scope.login = function() {
        executeAuth();
      }

      var successFunction = function() {
        getTechList();
        $scope.showLogin = false;
      }

      executeAuth();

      $scope.redirectUrl = function(techId) {
        var protocol = location.protocol + '//';
        var host = protocol + location.host;
        var path = location.pathname;
        if (path === '/') {
          path = '';
        }
        var servletRedirect = '/viewTech'
        var queryString = '?id=';
        return host + path + servletRedirect + queryString + techId;
      };

      function getTechList() {
        var protocol = location.protocol + '//';
        var host = location.host;
        var complement = '/_ah/api/';
        var rootUrl = protocol + host + complement;
        gapi.client.load('rest', 'v1', callBackLoaded, rootUrl);
        //                        $scope.techList = jsUtils.mockTechList();
      }

      function callBackLoaded() {
        gapi.client.rest.getTechnologies().execute(function(data) {
          $scope.techList = data.technologies;
          $scope.$apply();
        });
      }
    });

angular.module('techGallery').controller('techDetailsController',
    function($scope, $timeout, $modal) {

      $scope.idTechnology = jsUtils.getParameterByName('id');

      //Fill this property with the domain of your choice
      $scope.domain = "@ciandt.com";

      var alerts = jsUtils.alerts;

      var successFunction = function() {
        var protocol = location.protocol + '//';
        var host = location.host;
        var complement = '/_ah/api/';
        var rootUrl = protocol + host + complement;
        gapi.client.load('rest', 'v1', callBackLoaded, rootUrl);
        //                            fillTechnology(jsUtils.mockTechnology());
      }

      $timeout(function() {
        jsUtils.checkAuth(successFunction);
      }, 200);

      function callBackLoaded() {
        var idTech = $scope.idTechnology;
        var req = {
          id : idTech
        };
        gapi.client.rest.getTechnology(req).execute(function(data) {
          fillTechnology(data);
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
      }

      /*
       * 
       * Início da parte de recommend 
       * 
       */
      $scope.endorse = function() {
        var req = {};
        req.endorsed = $scope.endorsed;
        req.technology = $scope.idTechnology;
        if ($scope.endorsed) {
          gapi.client.rest.addEndorsement(req).execute(function(data) {
            var alert;
            if (data.hasOwnProperty('error')) {
              alert = alerts.failure;
              alert.msg = data.error.message;
            }else{
              alert = alerts.success;
            }
            $scope.alert = alert;
            $scope.endorsed = '';
            $scope.$apply();
          });
        }
      }

      /*
       * 
       * Início da parte de show Endorsement 
       * 
       */
      $scope.showAllEndorsers = function(endorsers) {
        return (endorsers.length > 0)
      }

      $scope.showEndorsementResponse = jsUtils.mockShowEndorsementResponse();

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
    });

angular.module('techGallery').controller('modalController',
    function($scope, $modalInstance, endorsers) {

      $scope.endorsers = endorsers;

      $scope.ok = function() {
        $modalInstance.close();
      };

    });
