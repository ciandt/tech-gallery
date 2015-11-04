module.exports = function($sce) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * The list of technologies
   * @type {Array}
   */
  this.technologies = [];

  /**
   * Retrieve list of technologies
   * @return {Promise} The gapi response
   */
  this.getTechnologies = function () {
    // gapi.client.rest.getTechnologies().execute(function (data) {
    //   gapi.client.rest.handleLogin().execute();
    //   context.technologies = data.technologies;
    // });

    // Mock
    context.technologies = [
      {
        'id': 'vagrant',
        'name': 'Vagrant Lorem Ipsum Dolor Sit Amet',
        'recommendation': 'Observar e fazer provas de conceito',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/vagrant.png',
        'shortDescription': 'O Vagrant tem a finalidade de gerenciar scripts de criação de ambientes.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'webgl',
        'name': 'WebGL',
        'recommendation': 'Observar e fazer provas de conceito',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/webgl.png',
        'shortDescription': 'WebGL (Web Graphics Library) é uma API JavaScript.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'unity',
        'name': 'Unity',
        'recommendation': 'Recomendada',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/unity.png',
        'shortDescription': 'Unity Application Block é um container de injeção de dependência parte da Enterprise Library da Microsoft.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'teamcity',
        'name': 'TeamCity',
        'recommendation': 'Usar e Aprender',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/teamcity.png',
        'shortDescription': 'Servidor de integração contínua extremamente amigável e fácil de usar.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'subversion',
        'name': 'Subversion (SVN)',
        'recommendation': 'Não recomendadas ou aposentar',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/subversion.png',
        'shortDescription': 'Apache Subversion (SVN) é um sistema de controle de versão centralizado que está sendo descontinuado no mercado.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'springframework',
        'name': 'Spring Framework',
        'recommendation': 'Recomendada',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/springframework.png',
        'shortDescription': 'Framework consolidado e padrão de mercado para injeção de dependência, controle de transações e desenvolvimento web.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'sass',
        'name': 'SASS',
        'recommendation': 'Observar e fazer provas de conceito',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/sass.png',
        'shortDescription': 'SAAS é uma linguagem de Stylesheets buscando facilitar o desenvolvimento de CSS.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'jenkins',
        'name': 'Jenkins',
        'recommendation': 'Recomendada',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/jenkins.png',
        'shortDescription': 'Ferramenta de integração contínua e automação de deployment.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'vagrant',
        'name': 'Vagrant',
        'recommendation': 'Observar e fazer provas de conceito',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/vagrant.png',
        'shortDescription': 'O Vagrant tem a finalidade de gerenciar scripts de criação de ambientes.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'webgl',
        'name': 'WebGL',
        'recommendation': 'Observar e fazer provas de conceito',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/webgl.png',
        'shortDescription': 'WebGL (Web Graphics Library) é uma API JavaScript.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'unity',
        'name': 'Unity',
        'recommendation': 'Recomendada',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/unity.png',
        'shortDescription': 'Unity Application Block é um container de injeção de dependência parte da Enterprise Library da Microsoft.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'teamcity',
        'name': 'TeamCity',
        'recommendation': 'Usar e Aprender',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/teamcity.png',
        'shortDescription': 'Servidor de integração contínua extremamente amigável e fácil de usar.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'subversion',
        'name': 'Subversion (SVN)',
        'recommendation': 'Não recomendadas ou aposentar',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/subversion.png',
        'shortDescription': 'Apache Subversion (SVN) é um sistema de controle de versão centralizado que está sendo descontinuado no mercado.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'springframework',
        'name': 'Spring Framework',
        'recommendation': 'Recomendada',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/springframework.png',
        'shortDescription': 'Framework consolidado e padrão de mercado para injeção de dependência, controle de transações e desenvolvimento web.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'sass',
        'name': 'SASS',
        'recommendation': 'Observar e fazer provas de conceito',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/sass.png',
        'shortDescription': 'SAAS é uma linguagem de Stylesheets buscando facilitar o desenvolvimento de CSS.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      },
      {
        'id': 'jenkins',
        'name': 'Jenkins',
        'recommendation': 'Recomendada',
        'image': 'https://storage.googleapis.com/tech-gallery-assets/imagesLogo/jenkins.png',
        'shortDescription': 'Ferramenta de integração contínua e automação de deployment.',
        'positiveRecommendationsCounter': 0,
        'negativeRecommendationsCounter': 0,
        'endorsersCounter': 0,
        'commentariesCounter': 0,
        'lastActivity': '28/10/2015 13:44'
      }
    ];

    return context.technologies;
  };

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
};
