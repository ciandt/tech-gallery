// jshint ignore:start
'use strict';

var gulp = require('gulp');
var jshint = require('gulp-jshint');
var csslint = require('gulp-csslint');
var gutil = require('gulp-util');
var del = require('del');
var sass = require('gulp-sass');
var rename = require('gulp-rename');
/**
 * Files path
 * @type {Object}
 */
var src = {
  scripts: {
    all: 'app/**/*.js',
    app: 'app/app.js'
  },
  styles: {
    all: 'assets/stylesheets/**/*.scss',
    app: 'assets/stylesheets/app.scss'
  }
};

/**
 * Build folder prefix
 * @type {String}
 */
var build = 'build/';

/**
 * Output folder
 * @type {Object}
 */
var out = {
  scripts: {
    file: 'app.min.js',
    folder: build
  },
  styles: {
    file: 'app.min.css',
    folder: build
  }
};


gulp.task('lint', ['jshint', 'csslint']);

gulp.task('clean', function () {
  return del([
    build
  ]);
});

gulp.task('build:stylesheets', function () {
  gulp.src(src.styles.app)
    .pipe(sass().on('error', gutil.log))
    // TODO: minify stylesheets
    .pipe(rename(out.styles.file))
    .pipe(gulp.dest(out.styles.folder));
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
