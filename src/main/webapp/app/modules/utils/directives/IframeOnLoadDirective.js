module.exports = function() {
  return {
    scope: {
      callback: "&iframeOnLoad"
    },
    link: function(scope, element) {
      element.on('load', function() {
        return scope.callback();
      });
    }
  }
}