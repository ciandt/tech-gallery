module.exports = function ($rootScope, AppService, TechnologyService, $stateParams) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;
  
  var alerts = {
	  success : {
		  type : 'success',
	  },
	  failure : {
		  type : 'danger',
	  },
	  caution : {
		  type : 'warning',
	  }
  };

  /**
   * Loading state
   * @type {Boolean}
   */
  this.loading = false;
  
  this.login = function(){
	  checkLogin(false);
  };
  
  AppService.setPageTitle('Adicionar nova tecnologia');
  
  var req = {id: $stateParams.id};
  
  TechnologyService.getTechnology(req).then(function(data){
	  if (data.hasOwnProperty('error')) {
		  context.showContent = false;
		  context.showTechNotExists = true;
		  context.$apply();
		  return;
	  }
	  fillTechnology(data);
  });
  
  this.addOrUpdateTechnology = function(){
      if(context.name != null && context.description != null && context.shortDescription != null) {
    	  var req = fillRequestToSave();
    	  TechnologyService.addOrUpdate(req).then(function(data){
    		  var alert;
    		  if (data.hasOwnProperty('error')) {
    			  alert = alerts.failure;
    			  alert.msg = data.error.message;
    		  }else{
    			  alert = alerts.success;
    			  clearTechnology();
    		  }
    		  context.alert = alert;
    		  context.$apply();
    	  });
      }
      callBackLoaded();
  };
  
  /*
   * Function to fill the request to save the technology.
   */
  function fillRequestToSave() {
	  if(context.image && context.image.startsWith('https://')){
          var req = {
        		  id : slugify(context.name),
        		  name : context.name,
        		  shortDescription : context.shortDescription,
        		  recommendationJustification : context.justification,
        		  recommendation : context.selectedRecommendation,
        		  description : context.description,
        		  website : context.webSite,
        		  image : context.image
          };
          return req;
        }else{
          var req = {
        		  id : slugify(context.name),
        		  name : context.name,
        		  shortDescription : context.shortDescription,
        		  recommendationJustification : context.justification,
        		  recommendation : context.selectedRecommendation,
        		  description : context.description,
        		  website : context.webSite,
        		  imageContent : context.image
          };
          return req;
        }
  }
  
  /*
   * Function to clean the technology informations
   */
  this.clearTechnology = function(){
	  context.name = '';
	  context.description = '';
	  context.shortDescription = '';
	  context.webSite = '';
      document.getElementById('idimage').value = null;
      document.getElementById('list').innerHTML = ['<img src="/assets/images/no_image.png" title="Insira uma imagem" width="200" />'].join('');
  }
  
  /*
   * Function to define the informations loaded on page.
   */
  function callBackLoaded() {
  	
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
	  context.$apply();
  }
  
  this.selectRecommendation = function(selected){
	  context.selectedRecommendation = selected;
  };
  
  function handleFileSelect(evt) {
      var files = evt.target.files;
      var f = files[0];
      var reader = new FileReader();
      reader.onload = (function(theFile) {
        return function(e) {
          var img = new Image;
          img.src = reader.result;
          img.onload = function() {
            if(f.type != 'image/png' || img.width > 355 || img.height > 355){
              alert('Esta imagem tem um tamanho ou tipo errado, escolha uma imagem com o tamanho 355x355 e tipo PNG.');
              document.getElementById('idimage').value = null;
              document.getElementById('list').innerHTML = ['<img src="/assets/images/no_image.png" title="Insira uma imagem" width="200" />'].join('');
            }else{
              var image = e.target.result;
              context.image = image.replace('data:image/png;base64,', '');
              document.getElementById('list').innerHTML = ['<img src="', e.target.result,'" title="', theFile.name, '" width="200" />'].join('');
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
