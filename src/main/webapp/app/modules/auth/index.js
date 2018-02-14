angular
  .module('TechGallery.Auth', [])
  .service('AuthService', require('./services/AuthService'))
  .directive('login', require('./directives/LoginDirective'))
  .constant('CLIENT_ID', '192387127763-mbfdnkegtcv169h4rsrn31fgn64mo21m.apps.googleusercontent.com')
  .constant('USER_SCOPES', 'https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/userinfo.email');

module.exports = 'TechGallery.Auth';
