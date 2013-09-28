'use strict';

/* Controllers */

angular.module('myApp.controllers', []).
  controller('MyCtrl1', [function() {

  }])
  .controller('ProductsCtrl', ['$scope', function(sc) {
    sc.products = [
      { id: 1, title: "Zapato Rojo", price: 21.50 },
      { id: 2, title: "Zapato Azul", price: 24.50 },
      { id: 3, title: "Zapato Azul", price: 24.50 },
      { id: 4, title: "Zapato Azul", price: 24.50 },
      { id: 5, title: "Zapato Azul", price: 24.50 },
      { id: 6, title: "Zapato Azul", price: 24.50 }
    ];

    sc.categories = [
      { title: "Pantalones", active: false },
      { title: "Remeras", active: true },
      { title: "Zapatos", active: false },
      { title: "Anteojos", active: false }
    ];


  }])
  .controller('ProductCtrl', ['$scope', '$routeParams', function(sc, rt) {
    // $rt.productId;

  }])
  .controller('MyCtrl2', [function() {

  }]);
