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
    context.loading = false;
    $scope.$apply();
  })

  /**
   * List of technologies
   * @type {Array}
   */
   this.getTechnologies = function(){
    context.loading = true;
    TechnologyService.getTechnologies().then(function(){
      context.items = TechnologyService.foundItems;
      context.loading = false;
    });
   }

   this.getTechnologies();

  /**
   * Loading state
   * @type {Boolean}
   */
   this.loading = true;

  this.recommendationFilter = null;
  this.orderFilter = null;
  this.lastActivityDateFilter = null;

   this.updateFilters = function(){
    context.loading = true;
    if(context.recommendationFilter === ''){
      context.recommendationFilter = null;
    }
    if(context.lastActivityDateFilter === ''){
      context.lastActivityDateFilter = null;
    }
    TechnologyService.setContentFilters(context.recommendationFilter, context.orderFilter, context.lastActivityDateFilter);
   }

  /**
   * Reset technologies filters
   * @return {Void}
   */
  this.resetFilters = function () {
    context.recommendationFilter = null;
    context.orderFilter = null;
    context.lastActivityDateFilter = null;
    context.updateFilters();
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
      context.loading = true;
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
