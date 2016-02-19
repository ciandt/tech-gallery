angular
  .module('TechGallery.Technology', [])
  .service('TechnologyService', require('./services/TechnologyService'))
  .controller('TechnologiesController', require('./controllers/TechnologiesController'))
  .controller('TechnologyController', require('./controllers/TechnologyController'))
  .controller('TechnologyAddController', require('./controllers/TechnologyAddController'))
  .controller('SearchController', require('./controllers/SearchController'))
  .config(require('./routes'));

module.exports = 'TechGallery.Technology';
