module.exports = function ($rootScope, AppService, TechnologyService, $stateParams, $state, $scope) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;

  /**
   * Loading state
   * @type {Boolean}
   */
  this.loading = false;

  this.login = function(){
	  checkLogin(false);
  };

  AppService.setPageTitle('Adicionar nova tecnologia');

  TechnologyService.getTechnology($stateParams.id).then(function(data){
	  if (data.hasOwnProperty('error')) {
		  context.showContent = false;
		  context.showTechNotExists = true;
		  return;
	  }
	  fillTechnology(data);
  });

  this.addOrUpdateTechnology = function(form){
    var isEdit = (context.id !== undefined);
    if(context.name != null && context.description != null && context.shortDescription != null) {
  	  TechnologyService.addOrUpdate(context).then(function(data){
  		  if (data.hasOwnProperty('error')) {
          AppService.setAlert(data.error.message, 'error');
  		  }else{
          if(context.addNew){
            clearTechnology();
            form.$setPristine();
            form.$setUntouched();
          }else {
            $state.go('root.technologies');
          }
          if(isEdit){
            AppService.setAlert('Tecnologia editada com sucesso', 'success');
          }else{
            AppService.setAlert('Tecnologia criada com sucesso', 'success');
          }
  		  }
  	  });
    }
  };

  /*
   * Function to clean the technology informations
   */
  function clearTechnology() {
	  context.name = '';
	  context.description = '';
	  context.shortDescription = '';
	  context.webSite = '';
    document.getElementById('technology-name').value = null;
    //document.getElementById('list').innerHTML = ['<img src="/assets/images/no_image.png" title="Insira uma imagem" width="200" />'].join('');
  }

  function fillTechnology(technology) {
	  context.name = technology.name;
	  context.id = technology.id;
	  context.shortDescription = technology.shortDescription;
	  context.description = technology.description;
	  context.webSite = technology.website;
	  context.image = technology.image;
	  if(context.image){
		  document.getElementById('list').innerHTML = ['<img src="', context.image,'" title="', context.name, '" width="200" />'].join('');
	  }
	  context.selectedRecommendation = technology.recommendation;
	  context.justification = technology.recommendationJustification;
  }

  this.selectRecommendation = function(selected){
	  context.selectedRecommendation = selected;
  };

  $scope.handleFileSelect = function(file) {
      var files = file.files;
      var f = files[0];
      var reader = new FileReader();
      reader.onload = (function(theFile) {
        return function(e) {
          var img = new Image;
          img.src = reader.result;
          img.onload = function() {
            if(f.type != 'image/png' || img.width > 100 || img.height > 100){
              alert('Esta imagem tem um tamanho ou tipo errado, escolha uma imagem com o tamanho 355x355 e tipo PNG.');
              document.getElementById('technology-image').value = null;
              //document.getElementById('list').innerHTML = ['<img src="/assets/images/no_image.png" title="Insira uma imagem" width="200" />'].join('');
            }else{
              var image = e.target.result;
              context.image = image.replace('data:image/png;base64,', '');
              //document.getElementById('list').innerHTML = ['<img src="', e.target.result,'" title="', theFile.name, '" width="200" />'].join('');
            }
          };
        };
      })(f);
      reader.readAsDataURL(f);
  }

  this.closeAlert = function() {
	  context.alert = undefined;
  };

  function setClassElement(id){
	  var elementClassIncrease = 'btn GPlusDefault';
      var elementClassDecrease = 'btn GPlusAdded';
      var elementClass = document.getElementById(id).className;
      if (elementClass.indexOf('GPlusAdded') < 0) {
    	  document.getElementById(id).className = elementClassDecrease;
      } else {
    	  document.getElementById(id).className = elementClassIncrease;
      }
  }
}
