angular
  .module('TechGallery.Core', [])
  .service('AppService', require('./services/AppService'))
  .controller('AppController', require('./controllers/AppController'));

module.exports = 'TechGallery.Core';
