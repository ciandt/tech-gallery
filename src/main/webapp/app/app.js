var angular = require('angular');

/**
 * Main module
 */
angular
  .module('TechGallery', [
    require('angular-sanitize'),
    require('angular-ui-bootstrap'),
    require('angular-ui-router'),
    require('./modules/utils'),
  ])
  .config(require('./routes'));
