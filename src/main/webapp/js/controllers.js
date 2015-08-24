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

var techListController = [
		'$scope',
		'$http',
		'$location',
		'$routeParams',
		'$route',
		function($scope, $http, $location, $routeParams) {

			$scope.techList = getTechList();

			$scope.populateList = function() {
				$scope.techList = getTechList();
			}

			$scope.showList = function() {
				$scope.showListTech = true;
			};

			$scope.hideList = function() {
				$scope.showListTech = false;
			};

			$scope.addTech = function() {
				var tech = {};
				tech.name = $scope.techName;
				tech.desc = $scope.techDesc;
				$scope.techList.push(tech);
				cleanList();
			}
			
			function cleanList() {
				$scope.techName = "";
				$scope.techDesc = "";
			}
			
			$scope.currentPage = 1;
			$scope.pageSize = 4;

			$scope.getPage = function(){
	            var begin = (($scope.currentPage - 1) * $scope.pageSize);
	            var end = begin + $scope.pageSize;
	            $scope.totalItems = $scope.techList.length;
	            $scope.techListFiltered = $scope.techList.slice(begin, end);
	        };
	        $scope.getPage();
          
	        $scope.pageChanged = function() {
	        	$scope.getPage(); 
	        };
	        
	        $scope.redirect = function(){
	        	$location.path('/techDetails');
	        };
			  
			function getTechList() {
				var descr = "Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá , depois divoltis porris, paradis. Paisis, filhis, espiritis santis.";
				var list = [ {
					name : "Angular",
					desc : descr,
					image : "/image/ANGULAR.png"
				}, {
					name : "Google App Engine",
					desc : descr,
					image : "/image/GAE.png"
				}, {
					name : "Google Compute Engine",
					desc : descr,
					image : "/image/GCE.png"
				}, {
					name : "Google Cloud Storage",
					desc : descr,
					image : "/image/GCS.png"
				}, {
					name : "Google Big Query",
					desc : descr,
					image : "/image/BQ.png"
				}, {
					name : "BootStrap",
					desc : descr,
					image : "/image/BOOT.png"
				} ];
				return list;
			}
		} ];
