(function () {
  'use strict';

 var myApp = angular.module('auction', ['ngRoute'])
    .config(['$routeProvider', function ($routeProvider) {
      var title = function (page) {
        return page + ' | Auction';
      };

      $routeProvider
        .when('/', {
          templateUrl: 'views/home.html',
          controller: 'HomeController',
          controllerAs: 'ctrl',
          title: title('Home')
        })
        .when('/search', {
          templateUrl: 'views/search.html',
          controller: 'SearchController',
          controllerAs: 'ctrl',
          title: title('Search')
        })
        .when('/product/:path/:id', {
              templateUrl: 'views/product.html',
              controller: 'ProductDetailsController',
              controllerAs: 'ctrl',
              title: title('Product'),
              resolve: {
                  product: ['$route', 'ProductService', function($route, ProductService){
                      return ProductService.getProductById(parseInt($route.current.params.id),
                          $route.current.params.path);
                  }]
              }
          })
        .otherwise({
           redirectTo: '/'
         });
    }])
    .run(['$rootScope', function ($rootScope) {
      $rootScope.$on('$routeChangeStart', function (event, next) {
        $rootScope.pageTitle = next.$$route.title;
      });
    }]);

    myApp.controller('PriceRangeCtrl', function($scope){

        $scope.price = new Price(50);

    });

    function Price(dollars){
        var price = dollars;
        this.__defineGetter__("price", function(){
            return price;
        });
        this.__defineSetter__("price", function(val){
            val = parseInt(val);
            price = val;
        });
    }
}());
  
