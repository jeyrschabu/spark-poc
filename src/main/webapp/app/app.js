'use strict';

angular.module('MainApp', ['ui.router', 'ngAnimate', 'ui.bootstrap']);
angular.module('MainApp').config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('home', {
            url:'/home',
            views: {
                'content':{
                    templateUrl: '/app/partials/home.html',
                    controller: 'HomeController'
                }
            }
        })

}]).run( function($rootScope) {
    $rootScope.$on('$locationChangeStart', function(){

    });
});
