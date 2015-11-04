module.exports = function ($sce) {
  return function(text) {
    return $sce.trustAsHtml(text.replace(/\n/g, '</p><p>'));
  };
}
