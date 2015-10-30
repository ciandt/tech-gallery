module.exports = function() {

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
      }
    ];

    return context.technologies;
  };
};
