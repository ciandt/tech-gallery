angular.module('techGallery').config([ '$translateProvider', function($translateProvider) {
	$translateProvider.translations('en-US', {
		'TITLE' : 'Tech Gallery',
		'TYPE_SEARCH' : 'Type in your search...',
		'ACCESS_LOGIN'	: 'Please login in order to have access to the content...',
		'WHO_KNOWS_ASK' : 'Do you know someone who is good at this technology?',
		'ENDORSE' : 'Endorse',
		'SKILL_LEVEL' : 'From Newbie to Jedi, how good are you at this technology?',
		'I_AM' : 'I am a',
		'WHO_IS_REFERENCE' : 'Who is reference?',
		'NO_ONE_KNOWS_YET' : 'No one here yet. Endorse someone who knows it up there!',
		'YOU_RECOMMEND_ASK' : 'Do you recommend this technology?',
		'COMMENTS' : 'Comments',
		'MAKE_COMMENT_TO_RECOMMEND' : 'Make a comment in order to complete your recommendation:',
		'CLEAR' : 'Clear',
		'MAKE_COMMENT' : 'Comment',
		'ON' : 'on',
		'AT' : 'às',
		'SAID' : 'said'
	});
	$translateProvider.translations('pt-BR', {
		'TITLE' : 'Tech Gallery',
		'TYPE_SEARCH' : 'Digite sua busca...',
		'ACCESS_LOGIN'	: 'Para ter acesso ao conteúdo realize seu login..',
		'WHO_KNOWS_ASK' : 'Quem manja muito desta tecnologia?',
		'ENDORSE' : 'Indicar',
		'SKILL_LEVEL' : 'Em uma escala de Newbie a Jedi, como você se avalia nesta tecnologia?',
		'I_AM' : 'Sou um',
		'WHO_IS_REFERENCE' : 'Quem é referência?',
		'NO_ONE_KNOWS_YET' : 'Ainda não tem ninguém aqui. Indique um colega que saiba disso ali em cima!',
		'YOU_RECOMMEND_ASK' : 'Recomenda esta tecnologia?',
		'COMMENTS' : 'Comentários',
		'MAKE_COMMENT_TO_RECOMMEND' : 'Para completar a recomendação desta tecnologia, faça um comentário:',
		'CLEAR' : 'Limpar',
		'MAKE_COMMENT' : 'Comentar',
		'ON' : 'em',
		'AT' : 'às',
		'SAID' : 'disse'
	});
	var lang = navigator.language;
	if(lang != 'pt-BR' && lang != 'en-US') {
		lang = 'en-US';
	} 
	$translateProvider.preferredLanguage(lang);
	
} ]);
