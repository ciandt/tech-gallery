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
        function($scope, $http, $location, $routeParams, $timeout, $rootScope) {

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
        function($scope, $http, $location, $routeParams, $timeout, $rootScope) {

            $scope.idTechnology = jsUtils.getParameterByName('id');

            //Fill this property with the domain of your choice
            $scope.domain = "@ciandt.com";

            var alerts = jsUtils.alerts;

            $scope.endorse = function() {
                var req = {};
                req.endorsed = $scope.endorsed + $scope.domain;
                req.technology = $scope.idTechnology;
                if ($scope.endorsed) {
                    console.log(req);
                    $scope.alert = alerts.caution;
                    $scope.endorsed = '';
                }
            }

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
        });