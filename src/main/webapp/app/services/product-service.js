'use strict';

angular
    .module('MainApp')
    .service('productService', function($http){
        function getProducts() {
            return $http({
                url: '/v1/products',
                method: 'GET'
            });
        }

        return {
            list : getProducts
        };
    });

