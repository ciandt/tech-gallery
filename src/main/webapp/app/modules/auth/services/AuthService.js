module.exports = function (CLIENT_ID, USER_SCOPES, UserService, $rootScope) {

  /**
   * Object context
   * @type {Object}
   */
  var context = this;
  var userDomain;
  var afterLogin;

  this.checkAuth = function(successFunction, immediate){
    afterLogin = successFunction;
    authenticate(immediate, handleAuthResultTrue);
  }

  function authenticate(immediate, callBackFunction){
    gapi.auth.authorize({
      client_id : CLIENT_ID,
      scope : USER_SCOPES,
      immediate : immediate,
      cookie_policy: 'single_host_origin'
    }, callBackFunction);
  }

  function handleAuthResultTrue(authResult) {
    if (authResult && !authResult.error) {
      if(!$rootScope.userEmail){
        UserService.getUserEmail(handleDomain, authResult)
      }else{
        handleDomain(authResult, verifyDomainGroup());
      }
    }else{
      return afterLogin(false);
    }
  }
  
  function handleDomain(authResult){
    if(verifyDomainGroup()){
      authResult.userEmail = $rootScope.userEmail;
      if(afterLogin){
          afterLogin(authResult);
          afterLogin = '';
      }
    }else{
      var responseError = {error: true,
          message: 'Este conteúdo é restrito a usuários autorizados, favor logar com seu domínio CI&T'};
      afterLogin(responseError);
      afterLogin = '';
    }
  }

  function verifyDomainGroup(){
    var domain = $rootScope.userEmail.split('@');
    userDomain = domain[1];
    if(userDomain == 'ciandt.com'){
      return true;
    }
    return false;
  }

  UserService.getUserEmail();

};
