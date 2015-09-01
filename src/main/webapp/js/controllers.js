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

angular
        .module('techGallery')
        .controller(
                'techListController',
                function($scope, $http, $location, $routeParams, $timeout,
                        $rootScope) {

                    $timeout(function() {
                        getTechList();
                    }, 200);

                    $scope.redirectUrl = function(techId) {
                        var host = 'http://' + location.host;
                        var path = location.pathname;
                        if (path === '/') {
                            path = '';
                        }
                        var servletRedirect = '/viewTech'
                        var queryString = '?id=';
                        return host + path + servletRedirect + queryString
                                + techId;
                    };

                    function getTechList() {
                        gapi.client.load('rest', 'v1', callBackLoaded,
                                'http://localhost:8888/_ah/api/');
                        // mockList();
                    }
                    ;

                    function adjustPagination() {
                        $scope.currentPage = 1;
                        $scope.pageSize = 4;
                        $scope.getPage();
                    }
                    ;

                    $scope.getPage = function() {
                        if ($scope.techList) {
                            var begin = (($scope.currentPage - 1) * $scope.pageSize);
                            var end = begin + $scope.pageSize;
                            $scope.totalItems = $scope.techList.length;
                            $scope.techListFiltered = $scope.techList.slice(
                                    begin, end);
                        }
                    };

                    $scope.pageChanged = function() {
                        $scope.getPage();
                    };

                    function mockList() {
                        var descr = "Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lÃ¡ , depois divoltis porris, paradis. Paisis, filhis, espiritis santis.";
                        var list = [ {
                            id : 1,
                            name : "Angular",
                            desc : descr,
                            image : "/image/ANGULAR.png"
                        }, {
                            id : 2,
                            name : "Google App Engine",
                            desc : descr,
                            image : "/image/GAE.png"
                        }, {
                            id : 3,
                            name : "Google Compute Engine",
                            desc : descr,
                            image : "/image/GCE.png"
                        }, {
                            id : 4,
                            name : "Google Cloud Storage",
                            desc : descr,
                            image : "/image/GCS.png"
                        }, {
                            id : 5,
                            name : "Google Big Query",
                            desc : descr,
                            image : "/image/BQ.png"
                        }, {
                            id : 6,
                            name : "BootStrap",
                            desc : descr,
                            image : "/image/BOOT.png"
                        } ];
                        $scope.techList = list;
                    }
                    ;

                    function callBackLoaded() {
                        gapi.client.rest.getTechnologies().execute(
                                function(data) {
                                    $scope.techList = data.technologies;
                                    adjustPagination();
                                    $scope.$apply();
                                });
                    }
                    ;
                });

angular
        .module('techGallery')
        .controller(
                'techDetailsController',
                function($scope, $http, $location, $routeParams, $timeout,
                        $rootScope) {
                    
                    $timeout(function() {
                        getTechnollogy();
                    },200);
                    
                    function getTechnollogy() {
                        gapi.client.load('rest', 'v1', callBackLoaded,
                                'http://localhost:8888/_ah/api/');
//                        mockTechnology();
                    };
                    
                    function mockTechnology(){
                        $scope.name = "Nome da tecnologia de id ";
                        $scope.description = "Suco de cevadiss, é um leite divinis, qui tem lupuliz, matis, aguis e fermentis. Interagi no mé, cursus quis, vehicula ac nisi. Aenean vel dui dui. Nullam leo erat, aliquet quis tempus a, posuere ut mi. Ut scelerisque neque et turpis posuere pulvinar pellentesque nibh ullamcorper. Pharetra in mattis molestie, volutpat elementum justo. Aenean ut ante turpis. Pellentesque laoreet mé vel lectus scelerisque interdum cursus velit auctor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ac mauris lectus, non scelerisque augue. Aenean justo massa.";
                        $scope.citDesc = "CI&T description";
                        $scope.image = "https://storage.googleapis.com/tech-gallery-assets/imagesLogo/adf.png";
                    };
                    
                    function callBackLoaded() {
                        var idTechnology = getParameterByName('id');
                        var req = {id:idTechnology};
                        gapi.client.rest.getTechnology(req).execute(
                                function(data) {
                                    $scope.name = data.name;
                                    $scope.description = data.description;
                                    $scope.citDesc = data.citDesc;
                                    $scope.image = data.image;
                                    $scope.$apply();
                                });
                    };
                    
                    function getParameterByName(name) {
                        name = name.replace(/[\[]/, "\\[").replace(/[\]]/,
                                "\\]");
                        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
                                .exec(location.search);
                        return results === null ? ""
                                : decodeURIComponent(results[1].replace(/\+/g,
                                        " "));
                    }
                });