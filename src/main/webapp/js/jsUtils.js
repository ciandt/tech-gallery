(function(window, undefined){
  'use strict';

  var clientId = '146680675139-6fjea6lbua391tfv4hq36hl7kqo7cr96.apps.googleusercontent.com';
  var scopes = 'https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/userinfo.email';
  var afterLogin;
  var userEmail;
  var userDomain;
  
  var getUserEmail = function(callBackFunction, authResult){
    setTimeout(function(){
      gapi.client.load('oauth2', 'v2', function() {
        gapi.client.oauth2.userinfo.get().execute(function(resp) {
          userEmail = resp.email;
          if(userEmail){
        	  trackUser(userEmail.replace('@'+resp.hd, ''));
          }
          if(callBackFunction){
            callBackFunction(authResult);
          }
        })
      });
    },200);
  }
  
  function trackUser(userEmail) {
  	ga('create', 'UA-60744312-3', 'auto');
  	ga('set', '&uid', userEmail);
  	ga('set', 'contentGroup1', userEmail);
  	ga('set', 'dimension1', userEmail);
  	ga('send', 'pageview');
  }
  
  getUserEmail();

  var checkAuth = function(successFunction, immediate){
    afterLogin = successFunction;
    auth(immediate, handleAuthResultTrue);
  }

  function auth(immediate, callBackFunction){
    gapi.auth.authorize({
      client_id : clientId,
      scope : scopes,
      immediate : immediate,
      cookie_policy: 'single_host_origin'
    }, callBackFunction);
  }

  function handleAuthResultTrue(authResult) {
    var authorizeButton = document
    .getElementById('authorize-button');
    if (authResult && !authResult.error) {
      if(!userEmail){
        getUserEmail(handleDomain, authResult)
      }else{
        handleDomain(authResult, verifyDomainGroup());
      }
    }else{
      return afterLogin(false);
    }
  }
  
  var verifyDomainGroup = function(){
    var domain = userEmail.split('@');
    userDomain = domain[1];
    if(userDomain == 'ciandt.com'){
      return true;
    }
    return false;
  }
  
  var handleDomain = function(authResult){
    if(verifyDomainGroup()){
      authResult.userEmail = userEmail;
      afterLogin(authResult);
      afterLogin = '';
    }else{
      var responseError = {error: true,
          message: 'Este conteúdo é restrito a usuários autorizados, favor logar com seu domínio CI&T'};
      afterLogin(responseError);
      afterLogin = '';
    }
  }
  
  var logoutRedirect = function(){
    var logoutRedirect = 'https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue='
    logoutRedirect += location.protocol;
    logoutRedirect += '//';
    logoutRedirect += location.hostname;
    logoutRedirect += location.pathname;
    logoutRedirect += location.search;
    return logoutRedirect;
  }

  var alerts = {
    success : {
      type : 'success',
      msg : 'Endorsement successfull!'
    },
    failure : {
      type : 'danger',
      msg : 'Usuário não encontrado!'
    },
    caution : {
      type : 'warning',
      msg : 'Você já fez essa indicação anteriormente!'
    }
  };

  var getParameterByName = function(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');

    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);

    if (results !== null) {
      return decodeURIComponent(results[1].replace(/\+/g, ' '));
    }

    return '';
  };
  
  var slugify = function(text){
    text = text.toString.toLowerCase();
    var from = "ãàáäâẽèéëêìíïîõòóöôùúüûñç·/_,:;";
    var to   = "aaaaaeeeeeiiiiooooouuuunc------";
    for (var i=0, l=from.length ; i<l ; i++) {
      text = text.replace(new RegExp(from.charAt(i), 'g'), to.charAt(i));
    }
    return text.replace(/\s+/g, '-')           // Replace spaces with -
      .replace(/[^a-zA-Z0-9_\-]+/g, '')       // Remove all non-word chars
      .replace(/\-\-+/g, '-')                 // Replace multiple - with single -
      .replace(/^-+/, '')                     // Trim - from start of text
      .replace(/-+$/, '');                    // Trim - from end of text
  }

  window.jsUtils = {
    checkAuth: checkAuth,
    getParameterByName: getParameterByName,
    alerts: alerts,
    logoutRedirect: logoutRedirect,
    getUserEmail : getUserEmail,
    slugify: slugify
  };

})(window);
