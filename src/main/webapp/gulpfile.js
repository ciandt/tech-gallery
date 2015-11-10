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
var streamify = require('gulp-streamify');
var connect = require('gulp-connect');
var chmod = require('gulp-chmod');

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

gulp.task('watch', [
  'clean',
  'watch:stylesheets',
  'watch:scripts'
], function() {
  gulp.watch(src.styles.all, ['watch:stylesheets']);
  gulp.watch(src.scripts.all, ['watch:scripts']);
});

gulp.task('build', [
  'clean',
  'build:stylesheets',
  'build:scripts'
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

gulp.task('webserver', function() {
  connect.server({
    livereload : true,
    port : 8088
  });
});

gulp.task('watch:stylesheets', function () {
  gulp.src(src.styles.app)
    .pipe(sass().on('error', function (err) {
      gutil.log(chalk.white.bgRed(' Error '));
      gutil.log(chalk.red(err.message));
    }))
    .pipe(rename(out.styles.fileMinified))
    .pipe(chmod(755))
    .pipe(gulp.dest(out.styles.folder))
});

gulp.task('watch:scripts', function () {
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
    .pipe(rename(out.scripts.fileMinified))
    .pipe(chmod(755))
    .pipe(gulp.dest(out.scripts.folder));
});

gulp.task('build:stylesheets', function () {
  gulp.src(src.styles.app)
    .pipe(sass().on('error', function (err) {
      gutil.log(chalk.white.bgRed(' Error '));
      gutil.log(chalk.red(err.message));
    }))
    .pipe(chmod(755))
    .pipe(gulp.dest(out.styles.folder))
    .pipe(rename(out.styles.fileMinified))
    .pipe(minifyCss())
    .pipe(chmod(755))
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
    .pipe(rename(out.scripts.fileMinified))
    .pipe(chmod(755))
    .pipe(gulp.dest(out.scripts.folder));
    //Disable the minification while ng-anotate is not working
    // .pipe(streamify(uglify()).on('error', function (err) {
    //   gutil.log(chalk.white.bgRed(' Error '));
    //   gutil.log(chalk.red(err.message));
    // }))
    // .pipe(gulp.dest(out.scripts.folder));
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
