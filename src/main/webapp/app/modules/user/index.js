require ('../project/controllers/ProjectModalController');

angular
  .module('TechGallery.User', ['project.modal'])
  .controller('UserController', require('./controllers/UserController'))
  .service('UserService', require('./services/UserService'))
  .config(require('./routes'));

module.exports = 'TechGallery.User';
