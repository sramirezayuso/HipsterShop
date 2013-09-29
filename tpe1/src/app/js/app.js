'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', ['myApp.filters', 'myApp.services', 'myApp.directives', 'myApp.controllers']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'});
    $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'MyCtrl2'});
    $routeProvider.when('/products', {templateUrl: 'partials/products.html', controller: 'ProductsCtrl'});
    $routeProvider.when('/products/:productId', {templateUrl: 'partials/product.html', controller: 'ProductCtrl'});
    $routeProvider.when('/cart', {templateUrl: 'partials/cart.html', controller: 'CartCtrl'});
    $routeProvider.when('/orders', {templateUrl: 'partials/orders.html', controller: 'OrdersCtrl'});
    $routeProvider.when('/orders/:orderno', {templateUrl: 'partials/order.html', controller: 'OrderCtrl'});
    $routeProvider.otherwise({redirectTo: '/home'});
  }]);
