module.exports = function (TechnologyService, AppService, UserService, $location) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  this.changeTextFilter = function(){
    TechnologyService.setTextFilter(context.textSearch);
  };

  this.searchTechnology = function (){
    TechnologyService.searchTechnologies().then(function(data){
      $location.path('technologies');
    });
  }

  this.logOutUser = UserService.logOutUser();

  /**
   * Page title
   */
   AppService.setPageTitle('Tecnologias');
 }
