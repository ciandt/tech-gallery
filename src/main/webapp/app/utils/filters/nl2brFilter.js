module.exports = function () {
  return function(text) {
    return text.replace(/\n/g, '<br>');
  };
}
