module.exports = function (
  API,
  CLIENT_ID,
  USER_SCOPES,
  $rootScope,
  $location
) {
  var context = this;

  this.init = function () {
    if ($rootScope.isGapiLoaded) {
      return;
    }

    gapi.client.load(API.NAME, API.VERSION, function (data) {
      $rootScope.isApiLoaded = true;
    }, API.URL);
  }
};
