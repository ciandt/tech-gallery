angular.module('techGallery').config([ '$translateProvider', function($translateProvider) {
	$translateProvider.translations('en-US', {
		'TITLE' : 'Tech Gallery ENG',
		'FOO' : 'This is a paragraph'
	});
	$translateProvider.translations('pt-BR', {
		'TITLE' : 'Tech Gallery',
		'FOO' : 'This is a paragraph'
	});
	var lang = navigator.language;
	if(lang != 'pt-BR' && lang != 'en-US') {
		lang = 'en-US';
	} 
	$translateProvider.preferredLanguage(lang);
	
} ]);
