(function () {
  'use strict';

  var ProductService = function ($http) {
    // Instance attributes go here:
    this.$http = $http;
  };

  /** List all dependencies required by the service. */
  ProductService.$inject = ['$http'];

  // Instance methods go here:
  ProductService.prototype = {

    /** Returns the list of all available products on the server. */
    getProducts: function () {
      return this.$http.get('data/products-featured.json')
          .then(function (resp) { return resp.data; });
    },

    /** Finds products with specified criteria.
      * NOTE: Search criteria are not implemented yet.
      */
    find: function () {
      return this.$http.get('data/products-search.json')
          .then(function (resp) { return resp.data; });
    },


      /** Finds products by their ID. */
      getProductById: function (productId, path) {
          var getData = (path === "featured") ? this.getProducts() : this.find();
          return getData.then(function (products) {
              return _.find(products, function (product) {
                  return product.id === productId;
              });
          });
      }
/*
      getProductById: function(productId, path){
          var getData = (path === "featured") ? this.getProducts() : this.find();
          var foundProduct = getData.then(function(data){
              for(var i = 0; i < data.length; i++) {
                  if(productId === data[i].id){
                      console.log(data[i]);
                      return data[i];
                  }
              }
          });
          return foundProduct;
      }

      */
  };

  // Register the service within AngularJS DI container.
  angular.module('auction').service('ProductService', ProductService);
}());
