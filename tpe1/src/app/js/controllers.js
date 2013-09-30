'use strict';

/* Controllers */

angular.module('myApp.controllers', []).
  controller('HomeCtrl', ['$scope', function(sc) {
    sc.products = [
      { title: "Camisa Leńadoras Abercrombie", price: 210.00 },
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
      { id: 1, title: "Zapato Rojo", price: 21.50, brand: "A"},
      { id: 2, title: "Azul el zapato", price: 26.00, brand: "A"},
      { id: 3, title: "Zapato Azul", price: 24.50, brand: "E"},
      { id: 4, title: "Verde la camiseta", price: 24.50, brand: "A"},
      { id: 5, title: "Zapato Azul", price: 14.50, brand: "B"},
      { id: 6, title: "Zapato Azul", price: 25.50, brand: "C"},
    ];

    sc.categories = [
      { title: "Pantalones", active: false },
      { title: "Remeras", active: true },
      { title: "Zapatos", active: false },
      { title: "Anteojos", active: false }
    ];

    sc.order = "brand";


  }])

  .controller('ProductCtrl', ['$scope', '$routeParams', function(sc, rp) {

    sc.categories = [
      { title: "Pantalones", active: false },
      { title: "Remeras", active: true },
      { title: "Zapatos", active: false },
      { title: "Anteojos", active: false }
    ];

    sc.product = {
      id: rp.productId,
      title: "Remeras Verdes",
      price: 40,
      brand: "Printashirt",
      color: "Verde",
      imageUrl: "http://rlv.zcache.com/omg_hipster_triangle_t_shirt-r9ec715764a9c4a4c840fc9de6bc978e3_8041a_512.jpg?bg=0xffffff"
    }

  }])

  .controller('CartCtrl', ['$scope', function(sc) {
    sc.products = [
      { title: "Zapatos", price: 21.50, size: 12, color: 'Rojo' },
      { title: "Zapatillas", price: 21.50, size: 12, color: 'Azul' },
      { title: "Ojotas", price: 21.50, size: 12, color: 'Amarillo' },
      { title: "Mocasines", price: 21.50, size: 12, color: 'Verde' },
      { title: "Botas", price: 21.50, size: 12, color: 'Violeta' },
    ];

    sc.runningTotal = function(){
      var runningTotal = 0;
      angular.forEach(sc.products, function(product, index){
        runningTotal += product.price;
      });
      return runningTotal;
    };
  }])

  .controller('OrdersCtrl', ['$scope', function(sc) {

    sc.orders = [
      { orderno: 21343,
        products: [
        { title: "Zapatos", price: 21.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 21.50, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 21.50, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 21.50, size: 12, color: 'Verde' },
        { title: "Botas", price: 21.50, size: 12, color: 'Violeta' },
      ]},
      { orderno: 34644,
        products: [
        { title: "Zapatos", price: 21.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 21.50, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 21.50, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 21.50, size: 12, color: 'Verde' },
        { title: "Botas", price: 21.50, size: 12, color: 'Violeta' },
      ]},
      { orderno: 23483,
        products: [
        { title: "Zapatos", price: 24.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 12.50, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 21.90, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 41.20, size: 12, color: 'Verde' },
        { title: "Botas", price: 54.50, size: 12, color: 'Violeta' },
      ]},
      { orderno: 18442,
        products: [
        { title: "Zapatos", price: 21.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 21.50, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 21.50, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 21.50, size: 12, color: 'Verde' },
        { title: "Botas", price: 21.50, size: 12, color: 'Violeta' },
      ]},
      { orderno: 78073,
        products: [
        { title: "Zapatos", price: 41.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 21.70, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 23.50, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 24.50, size: 12, color: 'Verde' },
        { title: "Botas", price: 31.50, size: 12, color: 'Violeta' },
      ]},
    ];

    sc.runningTotal = function(orderno){
      var runningTotal = 0;
      angular.forEach(sc.orders, function(order, index){
        if (order.orderno == orderno) {
          angular.forEach(order.products, function(product, index){
            runningTotal += product.price;
          })
        };
      });
      return runningTotal;
    };

  }])
.controller('AccessCtrl', ['$scope', '$routeParams', function(sc, rp) {


}])
.controller('OrderCtrl', ['$scope', '$routeParams', function(sc, rp) {

    sc.orderno = rp.orderno;

    sc.products = function(){
      var products =[]
      angular.forEach(sc.orders, function(order, index){
        if (order.orderno == sc.orderno) {
          products = order.products;
        };
      });
      return products;
    };

    sc.orders = [
      { orderno: 21343,
        products: [
        { title: "Zapatos", price: 21.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 21.50, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 21.50, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 21.50, size: 12, color: 'Verde' },
        { title: "Botas", price: 21.50, size: 12, color: 'Violeta' },
      ]},
      { orderno: 34644,
        products: [
        { title: "Zapatos", price: 21.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 21.50, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 21.50, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 21.50, size: 12, color: 'Verde' },
        { title: "Botas", price: 21.50, size: 12, color: 'Violeta' },
      ]},
      { orderno: 23483,
        products: [
        { title: "Zapatos", price: 24.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 12.50, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 21.90, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 41.20, size: 12, color: 'Verde' },
        { title: "Botas", price: 54.50, size: 12, color: 'Violeta' },
      ]},
      { orderno: 18442,
        products: [
        { title: "Zapatos", price: 21.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 21.50, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 21.50, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 21.50, size: 12, color: 'Verde' },
        { title: "Botas", price: 21.50, size: 12, color: 'Violeta' },
      ]},
      { orderno: 78073,
        products: [
        { title: "Zapatos", price: 41.50, size: 12, color: 'Rojo' },
        { title: "Zapatillas", price: 21.70, size: 12, color: 'Azul' },
        { title: "Ojotas", price: 23.50, size: 12, color: 'Amarillo' },
        { title: "Mocasines", price: 24.50, size: 12, color: 'Verde' },
        { title: "Botas", price: 31.50, size: 12, color: 'Violeta' },
      ]},
    ];

    sc.runningTotal = function(){
      var runningTotal = 0;
      angular.forEach(sc.orders, function(order, index){
        if (order.orderno == sc.orderno) {
          angular.forEach(order.products, function(product, index){
            runningTotal += product.price;
          })
        };
      });
      return runningTotal;
    };

  }])
;
