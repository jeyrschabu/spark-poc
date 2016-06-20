'use strict';

var HomeController = function($scope, $location, productService) {
    var self = this;

    $scope.pageTitle = 'Home';
    $scope.products = [];

    function currentTab(path) {
        return $location.path() === path;
    }


    HomeController.prototype.getProducts = function() {
        productService.list().then(function(response) {
            $scope.products = response.data;
        });
    };

    self.getProducts();

    /** expose functions on scope **/
    $scope.currentTab = currentTab;
};

HomeController.$inject = ['$scope', '$location', 'productService'];
angular.module('MainApp').controller('HomeController', HomeController);

