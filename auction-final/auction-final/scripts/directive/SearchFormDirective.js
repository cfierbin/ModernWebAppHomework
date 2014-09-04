/**
 * Created by Cristina on 23.08.2014.
 */

(function () {

    'use strict';

    var searchFormDirectiveFactory = function () {
        return {
            scope: false,
            restrict: 'E',
            templateUrl: 'views/partial/SearchFormDirective.html'
        };
    };

    //Register the directive inside AngularJS DI container
    angular.module('auction').directive('auctionSearchForm', searchFormDirectiveFactory);

}());
