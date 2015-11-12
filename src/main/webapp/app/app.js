var angular = require('angular');
var apiName = 'rest';
var apiVersion = 'v1';
var showGPlus = false;
if(window.location.hostname == 'localhost'){
  var port = ':8080';
} else {
  var port = '';
}
var apiUrl =  [
  window.location.protocol,
  '//',
  window.location.hostname,
  port,
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
    require('./modules/user')
  ])
  .config(require('./routes'))
  .constant('API', {
    'NAME' : apiName,
    'VERSION': apiVersion,
    'URL': apiUrl
  });
