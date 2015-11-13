module.exports = function ($scope, $rootScope, AppService, TechnologyService, UserService, $timeout, API) {

  /**
   * Object context
   * @type {Object}
   */
   var context = this;

  /**
   * Listner to update list when text filter is fired
   * @type {Array}
   */
   $scope.$on('searchChange', function(event, data) {
    context.items = data.technologies;
    $scope.$apply();
  })

  /**
   * List of technologies
   * @type {Array}
   */
   this.getTechnologies = function(){
    TechnologyService.getTechnologies().then(function(){
      context.items = TechnologyService.foundItems;
    });
   }

   this.getTechnologies();

  /**
   * Loading state
   * @type {Boolean}
   */
   this.loading = false;

   this.changeFilters = function(){
    if(context.recommendationFilter == ""){
      context.recommendationFilter = null;
    }
    if(context.lastActivityDateFilter == ""){
      context.lastActivityDateFilter = null;
    }
    TechnologyService.setContentFilters(context.recommendationFilter, context.orderFilter, context.lastActivityDateFilter);
   }

   this.followTechnology = function(idTechnology, $event){
    context.currentElement = $event.currentTarget;
      TechnologyService.followTechnology(idTechnology).then(function(data){
        if(!data.hasOwnProperty('error')){
          changeFollowedClass(context.currentElement);
        }
      });
   }

   function changeFollowedClass(element){
    if(element.className.indexOf('btn-default') > 0){
      element.className = 'btn btn-xs btn-danger';
    }else{
      element.className = 'btn btn-xs btn-default';
    }
    context.currentElement = undefined;
  }

  this.setFollowedClass = function(isFollowedByUser){
    if(isFollowedByUser){
      return 'btn btn-xs btn-danger';
    }
    return 'btn btn-xs btn-default';
  }

  this.deleteTechnology = function(idTechnology){
    if(confirm('Você realmente quer apagar a tecnologia?')) {
      TechnologyService.deleteTechnology(idTechnology).then(function(data){
        if(!data.hasOwnProperty('error')){
          context.getTechnologies();
        }
      });
    }
  }

  /**
   * Page title
   */
   AppService.setPageTitle('Tecnologias');
 }
