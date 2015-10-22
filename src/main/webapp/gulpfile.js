// jshint ignore:start
'use strict';

var gulp = require('gulp');
var jshint = require('gulp-jshint');
var csslint = require('gulp-csslint');
var del = require('del');
/**
 * Build folder prefix
 * @type {String}
 */
var build = 'build/';


gulp.task('lint', ['jshint', 'csslint']);

gulp.task('clean', function () {
  return del([
    build
  ]);
});

gulp.task('jshint', function() {
  return gulp.src('./js/*.js')
    .pipe(jshint())
    .pipe(jshint.reporter('checkstyle'));
});

gulp.task('csslint', function() {
  return gulp.src('./css/*.css')
    .pipe(csslint())
    .pipe(csslint.reporter('checkstyle-xml'));
});
