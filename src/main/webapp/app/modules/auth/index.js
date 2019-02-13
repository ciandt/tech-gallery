angular
  .module('TechGallery.Auth', [])
  .service('AuthService', require('./services/AuthService'))
  .directive('login', require('./directives/LoginDirective'))
  .constant('CLIENT_ID', '786344209909-40uq3aovg6gbdr1lrqs28kdlr4m5i14c.apps.googleusercontent.com')
  .constant('USER_SCOPES', 'https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/userinfo.email');

module.exports = 'TechGallery.Auth';
