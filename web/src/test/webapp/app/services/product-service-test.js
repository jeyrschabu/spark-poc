describe('MainApp', function () {

    var productService;
    var httpBackend = {};


    beforeEach(function () {
        module('MainApp');
    });

    describe ('productService', function () {
        beforeEach (inject(function (_productService_, $httpBackend) {
            productService = _productService_;
            httpBackend = $httpBackend;
        }));

        it ('should return a list of products', function() {
            var products = [{name : 'test-product1'}];

            httpBackend.whenGET('/v1/products').respond({
                products : products
            });
            productService.list().then(function(response) {
                expect(response.data.products).toEqual(products);
            });
            httpBackend.flush();
        });

        afterEach (function() {
            httpBackend.verifyNoOutstandingExpectation();
            httpBackend.verifyNoOutstandingRequest();
        });
    });
});
