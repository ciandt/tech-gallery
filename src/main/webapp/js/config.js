angular.module('techGallery').config([ '$translateProvider', function($translateProvider) {
	$translateProvider.translations('en', {
		'TITLE' : 'Tech Gallery',
		'FOO' : 'This is a paragraph'
	});

	$translateProvider.preferredLanguage('en');
} ]);
