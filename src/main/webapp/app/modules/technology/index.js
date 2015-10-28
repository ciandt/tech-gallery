angular
  .module('TechGallery.Technology', [])
  .service('TechnologyService', require('./services/TechnologyService'))
  .controller('TechnologiesController', require('./controllers/TechnologiesController'))
  .config(require('./routes'));

module.exports = 'TechGallery.Technology';
