// jshint ignore:start
'use strict';

var gulp = require('gulp');
var jshint = require('gulp-jshint');
var csslint = require('gulp-csslint');
var gutil = require('gulp-util');
var chalk = require('chalk');
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer');
var del = require('del');
var sass = require('gulp-sass');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var browserify = require('browserify');
var minifyCss = require('gulp-minify-css');

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
    file: 'app.js',
    fileMinified: 'app.min.js',
    folder: build
  },
  styles: {
    file: 'app.min.css',
    fileMinified: 'app.min.css',
    folder: build
  }
};

gulp.task('watch', ['build'], function() {
  gulp.watch(src.styles.all, ['build:stylesheets']);
  gulp.watch(src.scripts.all, ['build:scripts']);
});

gulp.task('build', [
  'clean',
  'minify'
]);

gulp.task('minify', [
  'minify:stylesheets',
  'minify:scripts'
]);

gulp.task('lint', [
  'jshint',
  'csslint'
]);

gulp.task('clean', function () {
  return del([
    build
  ]);
});

gulp.task('build:stylesheets', function () {
  gulp.src(src.styles.app)
    .pipe(sass().on('error', function (err) {
      gutil.log(chalk.white.bgRed(' Error '));
      gutil.log(chalk.red(err.message));
    }))
    .pipe(gulp.dest(out.styles.folder));
});

gulp.task('minify:stylesheets', ['build:stylesheets'], function () {
  gulp.src(out.styles.file)
    .pipe(rename(out.styles.fileMinified))
    .pipe(minifyCss())
    .pipe(gulp.dest(out.styles.folder));
});

gulp.task('build:scripts', function () {
  return browserify(src.scripts.app, {
      debug: true,
      insertGlobals: true
    })
    .bundle().on('error', function (err) {
      gutil.log(chalk.white.bgRed(' Error '));
      gutil.log(chalk.red(err.message));
      this.emit('end');
    })
    .pipe(source(out.scripts.file))
    .pipe(gulp.dest(out.scripts.folder));
});

gulp.task('minify:scripts', ['build:scripts'], function () {
  gulp.src(out.scripts.file)
    .pipe(rename(out.scripts.fileMinified))
    .pipe(uglify().on('error', function (err) {
      gutil.log(chalk.white.bgRed(' Error '));
      gutil.log(chalk.red(err.message));
    }))
    .pipe(gulp.dest(out.scripts.folder));
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
