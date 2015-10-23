angular
  .module('TechGallery.Utils', [])
  .filter('nl2br', require('./filters/nl2brFilter'))

module.exports = 'TechGallery.Utils';
