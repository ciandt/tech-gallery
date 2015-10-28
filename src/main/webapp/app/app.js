var angular = require('angular');
var apiName = 'rest';
var apiVersion = 'v1';
var apiUrl =  [
  window.location.protocol,
  '//',
  window.location.hostname,
  ':8080',
  '/_ah/api/'
].join('');

/**
 * Main module
 */
angular
  .module('TechGallery', [
    require('angular-sanitize'),
    require('angular-ui-bootstrap'),
    require('angular-ui-router'),
    require('./modules/core'),
    require('./modules/auth'),
    require('./modules/utils'),
    require('./modules/technology'),
  ])
  .config(require('./routes'))
  .constant('API', {
    'NAME' : apiName,
    'VERSION': apiVersion,
    'URL': apiUrl
  });
