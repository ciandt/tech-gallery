module.exports = function() {

  /**
   * Retrieve list of technologies
   * @return {Promise} The gapi response
   */
  this.getTechnologies = function () {
    return gapi.client.rest.getTechnologies();
  };
};
