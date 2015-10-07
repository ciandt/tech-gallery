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
