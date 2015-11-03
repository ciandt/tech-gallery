angular
  .module('TechGallery.Core', [])
  .controller('AppController', require('./controllers/AppController'))
  .service('AppService', require('./services/AppService'));

module.exports = 'TechGallery.Core';
