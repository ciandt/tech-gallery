angular
  .module('TechGallery.Auth', [])
  .service('AuthService', require('./services/AuthService'))
  .constant('CLIENT_ID', '146680675139-6fjea6lbua391tfv4hq36hl7kqo7cr96.apps.googleusercontent.com')
  .constant('USER_SCOPES', 'https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/userinfo.email');

module.exports = 'TechGallery.Auth';
