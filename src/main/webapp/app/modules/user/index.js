angular
  .module('TechGallery.User', [])
  .controller('UserController', require('./controllers/UserController'))
  .service('UserService', require('./services/UserService'))
  .config(require('./routes'));

module.exports = 'TechGallery.User';
