angular.module('techGallery').controller(
  'modalController',
  function($scope, $modalInstance, endorsers) {
    'use strict';

    $scope.endorsers = endorsers;

    $scope.ok = function() {
      $modalInstance.close();
    };
    
    $scope.redirectUserProfile = function(email) {
        var userId = email.split('@')[0];
        var protocol = location.protocol + '//';
        var host = protocol + location.host;
        var servletRedirect = '/userProfile.html';
        var queryString = '?userId=';
        return host + servletRedirect + queryString + userId;
      };
  }
);
