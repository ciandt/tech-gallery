angular.module('techGallery.directives', [])
  .directive('appVersion', ['version', function(version) {
    'use strict';

    return function(scope, elm) {
      elm.text(version);
    };
  }]);
