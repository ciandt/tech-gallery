angular.module('techGallery.filters', [])
  .filter('nl2br', function(){
    'use strict';

    return function(text) {
      return text.replace(/\n/g, '<br>');
    };
  });
