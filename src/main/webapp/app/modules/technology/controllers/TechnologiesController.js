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
   TechnologyService.getTechnologies().then(function(){
    context.items = TechnologyService.foundItems;
  });

  /**
   * Loading state
   * @type {Boolean}
   */
   this.loading = false;

   this.changeFilters = function(){
    TechnologyService.setContentFilters(context.recommendationFilter, context.orderFilter, context.lastActivityDateFilter);
   }

  /**
   * Page title
   */
   AppService.setPageTitle('Tecnologias');
 }
