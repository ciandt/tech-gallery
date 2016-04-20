angular
  .module('TechGallery.Auth', [])
  .service('AuthService', require('./services/AuthService'))
  .directive('login', require('./directives/LoginDirective'))
  .constant('CLIENT_ID', '923748949453-5dnd2m3fpnleuudufa61rnrngcamm6j6.apps.googleusercontent.com')
  .constant('USER_SCOPES', 'https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/userinfo.email');

module.exports = 'TechGallery.Auth';
