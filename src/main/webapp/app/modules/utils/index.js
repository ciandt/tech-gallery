angular
  .module('TechGallery.Utils', [])
  .filter('nl2br', require('./filters/nl2brFilter'))
  .directive('loading', require('./directives/LoadingDirective'));

module.exports = 'TechGallery.Utils';
