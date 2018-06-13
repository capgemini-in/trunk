'use strict';

var userApp = angular.module("myApp", [ 'ngRoute', 'ngResource' ]);

userApp.config(function($routeProvider) {
	$routeProvider.when('/users/new', {
	controller : 'NewUserCtrl',
	templateUrl : 'views/newuser.html'
	}).when('/users/:username', {
	controller : 'UsersByIdCtrl',
	templateUrl : 'views/userbyid.html'
	}).when('/users', {
	controller : 'UsersCtrl',
	templateUrl : 'views/users.html'
	}).otherwise({
	controller : 'SpaCtrl',
	templateUrl: 'views/spahome.html'
	    });
	});


