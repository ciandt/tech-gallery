module.exports = function ($filter) {
  return function(text) {
    return text.replace(/\n/g, '<br>');
  };
}
