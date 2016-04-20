module.exports = function($timeout) {
  return {
    scope: {
      callback: "&iframeOnLoad"
    },
    link: function(scope, element) {
      element.on('load', function() {
        $timeout(function() {          
          scope.callback();
        });
      });
    }
  }
}