angular
  .module('TechGallery.Utils', [])
  .filter('nl2p', require('./filters/nl2pFilter'))
  .filter('range', require('./filters/rangeFilter'))
  .directive('loading', require('./directives/LoadingDirective'));

module.exports = 'TechGallery.Utils';
