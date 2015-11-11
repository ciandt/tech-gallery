module.exports = function($q, $timeout, $rootScope) {

  /**
   * Object context
   * @type {Object}
   */
   var context = this;

   this.setTextFilter = function(textSearch){
    context.textSearch = textSearch;
  };

  this.setContentFilters = function(selectedRecommendationFilter, selectedOrderFilter, selectedLastActivityFilter){
    context.selectedRecommendationFilter = selectedRecommendationFilter;
    context.selectedOrderFilter = selectedOrderFilter;

    context.selectedLastActivityFilter = selectedLastActivityFilter;

    context.searchTechnologies();
  }

  /**
   * Retrieve list of technologies
   * @return {Promise} The gapi response
   */
   this.getTechnologies = function () {
    var deferred = $q.defer();
    gapi.client.rest.getTechnologies().execute(function (data) {
     gapi.client.rest.handleLogin().execute();
     context.foundItems = data.technologies;
     deferred.resolve(context.foundItems);
   });
    return deferred.promise;
  };

  this.searchTechnologies = function(){
    context.foundItems = [];
    var req = {
      titleContains: context.textSearch,
      shortDescriptionContains: context.textSearch,
      orderOptionIs: context.selectedOrderFilter,
      dateFilter : context.selectedLastActivityFilter,
      recommendationIs: context.selectedRecommendationFilter
    }
    var deferred = $q.defer();
    gapi.client.rest.findByFilter(req).execute(function(data){
      context.foundItems = data.technologies;
      $rootScope.$broadcast('searchChange', {
        technologies: context.foundItems
      });
      deferred.resolve(context.foundItems);
    });
    return deferred.promise;
  }

  /**
   * The single technology
   * @type {Object}
   */
   this.technology = {};

  /**
   * Retrive a technology based on its ID
   * @param  {String}   id The ID of the technology
   * @param  {Function} cb A callback function
   * @return {Void}
   */
   this.getTechnology = function (id, cb) {
    if (!id) {
      throw 'getTechnology needs a valid `id` parameter';
    }

    // Mock
    context.technology = {
      'id': id,
      'name': 'Vagrant',
      'recommendation': 'Observar e fazer provas de conceito',
      'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/vagrant.png',
      'description': 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Saepe ipsam pariatur atque ea rerum aliquid a laborum, illo soluta esse sit sunt natus autem ad sed repellendus fugit velit consequatur eveniet, dolorum itaque quisquam. Provident nesciunt, vitae sit quidem officia quasi quos aliquid dolorem explicabo id numquam aliquam suscipit at aperiam, in illum deleniti aspernatur dolores molestias doloribus laudantium, reiciendis corrupti molestiae consectetur. \nNon architecto sed, quam reprehenderit. Molestias suscipit natus ad sunt dolorum velit molestiae unde animi quasi voluptates recusandae, ipsum nemo perspiciatis reiciendis, consectetur minima dolores architecto odit maxime quibusdam temporibus doloribus alias ut autem! Nam, quis obcaecati ipsum alias delectus facere dolores nisi recusandae, maxime excepturi eos repudiandae fugiat molestias consequatur quidem! Cupiditate explicabo, fugit sit voluptates voluptatem a eos velit molestiae vitae, facilis dicta, repellat totam est quia ipsa modi id deleniti atque. Quam odio ex minima nobis nisi perferendis dignissimos deleniti voluptates et molestias est natus laborum facere, explicabo blanditiis vero magni reprehenderit. \nSequi accusamus optio repudiandae illo ad placeat cumque cupiditate sapiente aperiam eaque inventore ea quam, atque at ut, consequatur iusto earum id iste autem aliquid veritatis tenetur. Neque ut soluta, hic. Quisquam quidem nam minus dignissimos hic quia praesentium unde voluptatibus eligendi.',
      'shortDescription': 'O Vagrant tem a finalidade de gerenciar scripts de criação de ambientes.',
      'positiveRecommendationsCounter': 0,
      'negativeRecommendationsCounter': 0,
      'endorsersCounter': 0,
      'commentariesCounter': 0,
      'lastActivity': '28/10/2015 13:44',
      'website': 'https://www.vagrantup.com/'
    }

    if (cb && typeof cb === 'function') {
      cb(context.technology);
    }
  }

  /**
   * Ratings for user skill on a technology
   * @return {Array} The list of rating objects with value and title
   */
   this.getRatings = function () {
    return [
    {
      value: 1,
      title : 'Newbie'
    },
    {
      value: 2,
      title : 'Iniciante'
    },{
      value: 3,
      title : 'Padawan'
    },{
      value: 4,
      title : 'Knight'
    },{
      value: 5,
      title : 'Jedi'
    },
    ];
  }

  this.getRecommended = function () {
    // Mock
    return false;
  }
};
