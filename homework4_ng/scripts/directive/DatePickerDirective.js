/**
 * Created by Cristina on 23.08.2014.
 */

(function () {
    'use strict';

    //Factory function used to create the directive
    var datepickerDirectiveFactory = function () {
        return {
            scope: false,
            restrict: 'A', //allow to use the directive only as an attribute
            link: function (scope, element) {
                element.datepicker(); //datepicker() method added by bootstrap-datepicker package
            }
        };
    };

    //Register the directive inside AngularJS DI container
    angular.module('auction').directive('auctionDatepicker', datepickerDirectiveFactory);
}());
