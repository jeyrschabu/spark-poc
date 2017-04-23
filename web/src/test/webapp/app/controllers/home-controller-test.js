describe('MainApp', function(){
    var scope,
        location,
        homeController,
        mockProductService;

    beforeEach(function () {
        module('MainApp');
    });

    describe('HomeController', function(){
        beforeEach(inject(function($rootScope, $controller, $location, _productService_) {
            scope = $rootScope.$new();
            mockProductService = _productService_;
            location = $location;

            homeController = $controller('HomeController', {
                '$scope' : scope,
                '$location' : location,
                'productService' : mockProductService

            });

            spyOn(mockProductService, 'list').and.callFake(function() {
                return {
                    then: function(callback) { return callback({}); }
                };
            });
        }));

        it ('should have a title to match the page', function() {
            expect(scope.pageTitle).toBe('Home');
        });

        it ('should determine active tab based on current path', function() {
            expect(scope.currentTab('/home')).toBe(false);
            location.path('/home');
            expect(scope.currentTab('/home')).toBe(true);
        });

        it ('should get a list of products', function() {
            homeController.getProducts();
            expect(mockProductService.list).toHaveBeenCalled();
        });
    })
});
