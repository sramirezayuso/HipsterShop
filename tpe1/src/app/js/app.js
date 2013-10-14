'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', ['myApp.filters', 'myApp.services', 'myApp.directives', 'myApp.controllers', 'ngCookies']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'});
    $routeProvider.when('/products', {templateUrl: 'partials/products.html', controller: 'ProductsCtrl', reloadOnSearch: false});
    $routeProvider.when('/products/:productId', {templateUrl: 'partials/product.html', controller: 'ProductCtrl'});
    $routeProvider.when('/cart', {templateUrl: 'partials/cart.html', controller: 'CartCtrl'});
    $routeProvider.when('/access', {templateUrl: 'partials/access.html', controller: 'AccessCtrl'});
    $routeProvider.when('/orders', {templateUrl: 'partials/orders.html', controller: 'OrdersCtrl'});
    $routeProvider.when('/orders/:orderno', {templateUrl: 'partials/order.html', controller: 'OrderCtrl'});
    $routeProvider.when('/checkout', {templateUrl: 'partials/checkout.html', controller: 'CheckoutCtrl'});
    $routeProvider.otherwise({redirectTo: '/home'});
  }]);
