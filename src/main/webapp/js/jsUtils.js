var clientId = '146680675139-6fjea6lbua391tfv4hq36hl7kqo7cr96.apps.googleusercontent.com';
var scopes = 'https://www.googleapis.com/auth/plus.me';

var checkAuth = function(successFunction){
    afterLogin = successFunction;
    auth(true, handleAuthResultTrue);
}

function auth(immediate, callBackFunction){
    gapi.auth.authorize({
        client_id : clientId,
        scope : scopes,
        immediate : immediate
    }, callBackFunction);
}

function handleAuthResultTrue(authResult) {
    var authorizeButton = document
            .getElementById('authorize-button');
    if (authResult && !authResult.error) {
        afterLogin();
        afterLogin = '';
    } else {
        auth(false, handleAuthResultFalse);
    }
}

function handleAuthResultFalse(authResult){
    afterLogin();
    afterLogin = '';
}

var mockTechList = function(){
    var descr = "Mussum ipsum cacilds, vidis litro abertis. Consetis adipiscings elitis. Pra lá, depois divoltis porris, paradis. Paisis, filhis, espiritis santis.";
    var list = [ {
        id : 1,
        name : "Angular",
        desc : descr,
        image : "/image/ANGULAR.png"
    }, {
        id : 2,
        name : "Google App Engine",
        desc : descr,
        image : "/image/GAE.png"
    }, {
        id : 3,
        name : "Google Compute Engine",
        desc : descr,
        image : "/image/GCE.png"
    }, {
        id : 4,
        name : "Google Cloud Storage",
        desc : descr,
        image : "/image/GCS.png"
    }, {
        id : 5,
        name : "Google Big Query",
        desc : descr,
        image : "/image/BQ.png"
    }, {
        id : 6,
        name : "BootStrap",
        desc : descr,
        image : "/image/BOOT.png"
    } ];
    return list;
}

var mockTechnology = function() {
    var technology = {};
    technology.name = "Nome da tecnologia de id ";
    technology.description = "Suco de cevadiss, é um leite divinis, qui tem lupuliz, matis, aguis e fermentis. Interagi no mé, cursus quis, vehicula ac nisi. Aenean vel dui dui. Nullam leo erat, aliquet quis tempus a, posuere ut mi. Ut scelerisque neque et turpis posuere pulvinar pellentesque nibh ullamcorper. Pharetra in mattis molestie, volutpat elementum justo. Aenean ut ante turpis. Pellentesque laoreet mé vel lectus scelerisque interdum cursus velit auctor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ac mauris lectus, non scelerisque augue. Aenean justo massa.";
    technology.recommendation = "Digite aqui a recomendação da sua empresa para esta tecnologia";
    technology.image = "https://storage.googleapis.com/tech-gallery-assets/imagesLogo/adf.png";
    return technology;
}

var alerts = {
        success : {
            type : 'success',
            msg : 'Indicação efetuada!'
        },
        failure : {
            type : 'error',
            msg : 'Usuário não encontrado!'
        },
        caution : {
            type : 'warning',
            msg : 'Você já fez essa indicação anteriormente!'
        }
    }

var getParameterByName = function(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/,
            "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
            .exec(location.search);
    return results === null ? ""
            : decodeURIComponent(results[1].replace(/\+/g,
                    " "));
}

var jsUtils = {};
jsUtils.checkAuth = checkAuth;
jsUtils.mockTechList = mockTechList;
jsUtils.mockTechnology = mockTechnology;
jsUtils.getParameterByName = getParameterByName;
jsUtils.alerts = alerts;
