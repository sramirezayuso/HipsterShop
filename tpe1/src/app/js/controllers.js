'use strict';

/* Controllers */

angular.module('myApp.controllers', []).
  controller('MyCtrl1', [function() {

  }])
  .controller('ProductsCtrl', ['$scope', function(sc) {
    sc.products = [
      { title: "Zapato Rojo", price: 21.50 },
      { title: "Zapato Rojo", price: 21.50 },
      { title: "Zapato Rojo", price: 21.50 },
      { title: "Zapato Rojo", price: 21.50 },
      { title: "Zapato Rojo", price: 21.50 },
      { title: "Zapato Azul", price: 24.50 }
    ];

    sc.categories = [
      { title: "Pantalones", active: false },
      { title: "Remeras", active: true },
      { title: "Zapatos", active: false },
      { title: "Anteojos", active: false }
    ];


  }])
  .controller('MyCtrl2', [function() {

  }]);
