angular
  .module('TechGallery.User', [])
  .service('UserService', require('./services/UserService'))
  .config(require('./routes'));

module.exports = 'TechGallery.User';
