(function () {
  'use strict';

  angular.module('auction', ['ngRoute'])
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
      .when('/product/:productId', {
          templateUrl: 'views/product.html',
          controller: 'ProductController',
          controllerAs: 'ctrl',
          title: title('Product Details'),

          resolve: {

               product: ['$route', 'ProductService', function ($route, productService) {
                   var productId = parseInt($route.current.params.productId);

                  if (typeof(eticheta) === 'undefined') {
                      return productService.getProductById($route, productId);
                  }
                   if (eticheta == 'home') {
                       //read from /data/products-featured.json
                       return productService.getProductById($route, productId);
                    }
                   if (eticheta == 'search')  {
                       //read from data/products-search.json
                       return productService.getSearchProductById($route, productId);
                   }

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
}());
  
