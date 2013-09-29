'use strict';

/* Controllers */

angular.module('myApp.controllers', []).
  controller('HomeCtrl', ['$scope', function(sc) {
    sc.products = [
      { title: "Camisa Leñadoras Abercrombie", price: 210.00 },
      { title: "Vestido Minifalda Negro de Encaje y Jersey", price: 170.00 },
      { title: "Jean Elastizado Chupín Tiro Medio Óxido", price: 120.00 },
      { title: "Cartera Gamuza Multicolor Con Dos Bolsillos", price: 450.00 },
      { title: "Buzo Gap Hombre", price: 320.00 },
      { title: "Zapato Punta Priamo", price: 500.00 },
    ];

    sc.categories1 = [
      { title: "Pantalones"},
      { title: "Remeras"},
      { title: "Zapatos"},
      { title: "Anteojos"},
    ];

	 sc.categories2 = [
	  { title: "Camisas"},
      { title: "Camperas"},
      { title: "Chalecos"},
      { title: "Bufandas"}
    ];

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
  .controller('ProductCtrl', ['$scope', '$routeParams', function(sc, rp) {
  // .controller('ProductCtrl', ['$scope', function(sc) {

    sc.categories = [
      { title: "Pantalones", active: false },
      { title: "Remeras", active: true },
      { title: "Zapatos", active: false },
      { title: "Anteojos", active: false }
    ];

    sc.product = {
      id: rp.productId,
      title: "Remeras Verdes"
    }

  }])
  .controller('MyCtrl2', [function() {

  }]);
