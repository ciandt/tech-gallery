module.exports = function (TechnologyService, AppService, UserService) {

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
      console.log(data, TechnologyService.foundItems);
    });
  }

  this.logOutUser = UserService.logOutUser();

  /**
   * Page title
   */
   AppService.setPageTitle('Tecnologias');
 }
