'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
  .controller('PageHeaderCtrl', ['$rootScope', '$scope', '$cookieStore', 'ajaxService',  function(rt, sc, cs, as) {

    sc.loggedIn = false;
    rt.$on('refreshUser', function() {
      if (cs.get('authToken')) {
        sc.loggedIn = true;
        sc.user = {}
        sc.user.firstName = cs.get('user.firstName');
      }
    });

    rt.$emit('refreshUser');

    sc.logOut = function() {
      sc.loggedIn = false;
      cs.remove('authToken');
      cs.remove('user.id');
      cs.remove('user.firstName');
      rt.$emit('refreshUser');
      as.async('Account', { method: 'SignOut' }).then(function(response) {});
    }
  }])

  .controller('HomeCtrl', function($scope, ajaxService){
	var sc = $scope;

	function genreInCategory(genre, category){
		for(var i = 0; i < category.attributes.length; i++){
			var att = category.attributes[i];
			if(att.name == "Genero") {
				for(var j = 0; j < att.values.length; j++) {
					if(att.values[j] == genre)
						return true;
				}
				return false;
			}
		}
		return true;
	}

  	ajaxService.async('Catalog', {method: 'GetAllCategories'} ).then(function(response) {
		var categories = response.data.categories;
		var i, j=0, k=0;
		var maxCategories = 12;

		//Initialize Arrays
		sc.categoriesM = new Array();
		sc.categoriesH = new Array();

		//Add available categories to each genre-array
		for(i = 0; i < categories.length; i++) {
			if(j <= maxCategories && genreInCategory('Femenino',categories[i])) {
				sc.categoriesM[j++] = categories[i];
			}

			if(k <= maxCategories && genreInCategory('Masculino',categories[i])) {
				sc.categoriesH[k++] = categories[i];
			}
		}

		//Divide each category into 2
		var div = sc.categoriesM.length/2 + 1;
		sc.categories1M = sc.categoriesM.slice(0, div);
		sc.categories2M = sc.categoriesM.slice(div);
		sc.categories1H = sc.categoriesH.slice(0, div);
		sc.categories2H = sc.categoriesH.slice(div);
    });

	var filt = [ { "id": 5, "value": "Oferta" } ];

	ajaxService.async('Catalog', {method: 'GetAllProducts', filters: filt} ).then(function(response) {
		sc.products = response.data.products;
	});

  })

  .controller('ProductsCtrl', ['$scope', '$routeParams', 'ajaxService', function(sc, rp, as) {
    var categoryFilters = [ ];
    var productFilters = [];

    switch(rp.gender) {
      case "F" :
        categoryFilters.push({"id":1,"value":"Femenino"});
        productFilters.push({"id":1,"value":"Femenino"});
      break;
      case "M":
        categoryFilters.push({"id":1,"value":"Masculino"});
        productFilters.push({"id":1,"value":"Masculino"});
      break;
    }

    sc.categories = [ ];
  	as.async('Catalog', {method: 'GetAllCategories', filters: categoryFilters}).then(function(response) {
      response.data.categories.forEach(function(category) {
        sc.categories.push({ title: category.name, active: category.id == rp.categoryId });
      });
    });


    // This search depends if there is a category, or a subcategory, or a search by name
    sc.products = [ ];
  	as.async('Catalog', {method: 'GetProductsByCategoryId', id: rp.categoryId, filters: productFilters}).then(function(response) {
      response.data.products.forEach(function(product) {
        var brand = product.attributes.filter(function(attr) { return attr.id == 9; })[0].values[0];
        sc.products.push({ id: product.id, title: product.name, price: product.price, brand: brand, imageUrl: product.imageUrl[0]});
        console.log(product)
      });
    });

    sc.order = "brand";
  }])

  .controller('ProductCtrl', ['$scope', '$routeParams', 'ajaxService', function(sc, rp, as) {
    sc.changeImage = function(imgUrl) {
      sc.currentImage = imgUrl;
    }

    as.async('Catalog', {method: 'GetProductById', id: rp.productId} ).then(function(response) {
      sc.product = response.data.product;
      var att = response.data.product.attributes;

      sc.currentImage = sc.product.imageUrl[0];

      for(var i = 0; i < att.length ; i++) {
        switch(att[i].id) {
          case 4: sc.product.color = att[i].values[0]; break;
          case 9: sc.product.brand = att[i].values[0]; break;
          case 8: sc.product.matter = att[i].values[0]; break;
          case 7: sc.product.size = att[i].values; break;
        }
      }

	})

    sc.categories = [
      { title: "Pantalones", active: false },
      { title: "Remeras", active: true },
      { title: "Zapatos", active: false },
      { title: "Anteojos", active: false }
    ];

  }])

  .controller('CartCtrl', function($scope, ajaxService) {

    /*$scope.testUser = {
      username: "MattHarvey",
      password: "nymetsharvey",
      firstName: "Matt",
      lastName: "Harvey",
      gender: "M",
      identityCard: "00000000",
      email: "matt@harvey.com",
      birthDate: "1980-01-01"
    };*/

    ajaxService.async('Account', {method: 'SignIn', username: 'MattHarvey', password: 'nymetsharvey'} ).then(function(response) {
      $scope.authToken = response.data.authenticationToken;
      ajaxService.async('Order', {method: 'GetOrderById', username: 'MattHarvey', id: 6, authentication_token: $scope.authToken} ).then(function(response) {
        console.log(response);
        $scope.products = response.data.order.items;
      });
    });

    $scope.products = [];
    /*sc.products = [
      { title: "Zapatos", price: 21.50, size: 12, color: 'Rojo' },
      { title: "Zapatillas", price: 21.50, size: 12, color: 'Azul' },
      { title: "Ojotas", price: 21.50, size: 12, color: 'Amarillo' },
      { title: "Mocasines", price: 21.50, size: 12, color: 'Verde' },
      { title: "Botas", price: 21.50, size: 12, color: 'Violeta' },
    ];*/

    $scope.runningTotal = function(){
      var runningTotal = 0;
      angular.forEach($scope.products, function(product, index){
        runningTotal += product.price;
      });
      return runningTotal;
    };

    $scope.remove = function( idx ) {
      $scope.products.splice(idx, 1);
    };

  })

  .controller('OrdersCtrl', ['$scope', '$location', function(sc, loc) {

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

    sc.goOrder = function ( orderno ) {
      loc.path( '/orders/' + orderno );
    };

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
.controller('AccessCtrl', ['$rootScope', '$scope', '$routeParams', 'ajaxService', '$cookieStore', '$location', function(rt, sc, rp, as, cs, lc) {
    sc.submittedSignUp = false;
    sc.submittedSignIn = false;

    sc.signIn = function() {
      sc.submittedSignIn = true;
      if (sc.signinForm.$valid) {
        as.async('Account', { method: 'SignIn', username: sc.signin.username, password: sc.signin.password }).then(function(response) {
          if (response.data.error) {

          } else {
            cs.put('authToken', response.data.authenticationToken);
            cs.put('user.id', response.data.account.id);
            cs.put('user.firstName', response.data.account.firstName);
            console.log('new cookie authToken:' + response.data.authenticationToken);
            rt.$emit('refreshUser');
            lc.path('#products')
          }
        });
      }

    }

    sc.signUp = function() {
      sc.submittedSignUp = true;

      var params = { account: sc.signup || {}};
      params.account["birthDate"] = "1980-01-01";

      params.method = 'CreateAccount';
      as.async('Account', params).then(function(response) {
        if (response.data.error) {
          console.log(response.data.error);
        } else {
          // Need to sign in after account creation.
          as.async('Account', { method: 'SignIn', username: sc.signup.username, password: sc.signup.password }).then(function(response) {
            // We assume everything went okay.
            if (!response.data.error) {
              cs.put('authToken', response.data.authenticationToken);
              cs.put('user.id', response.data.account.id);
              cs.put('user.firstName', response.data.account.firstName);
              console.log('new cookie authToken:' + response.data.authenticationToken);
              rt.$emit('refreshUser');
              lc.path('#products')
            }
          });
        }
      });
    }

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

  .controller('CheckoutCtrl', ['$scope', function(sc) {

    sc.products = [
      { title: "Zapatos", price: 21.50, size: 12, color: 'Rojo' },
      { title: "Zapatillas", price: 21.50, size: 12, color: 'Azul' },
      { title: "Ojotas", price: 21.50, size: 12, color: 'Amarillo' },
      { title: "Mocasines", price: 21.50, size: 12, color: 'Verde' },
      { title: "Botas", price: 21.50, size: 12, color: 'Violeta' },
    ];

    sc.addresses = [
      { name: "Juan", street: "Rivadavia", door: 4060, floor: 1, apartment: "E", province: "Buenos Aires", city: "Capital Federal", zipCode: "13324", phone: "4892-2343" },
      { name: "Pedro", street: "Rivadavia", door: 4060, floor: 1, apartment: "E", province: "Buenos Aires", city: "Capital Federal", zipCode: "13324", phone: "4243-2343" },
      { name: "Juan", street: "Rivadavia", door: 4060, floor: 1, apartment: "E", province: "Buenos Aires", city: "Capital Federal", zipCode: "13324", phone: "4892-2343" }
    ];

    sc.currentAddr = {};

    sc.runningTotal = function(){
      var runningTotal = 0;
      angular.forEach(sc.products, function(product, index){
        runningTotal += product.price;
      });
      return runningTotal;
    };

    sc.loadSavedAddress = function( idx ) {
      sc.currentAddr = JSON.parse(JSON.stringify(sc.addresses[idx]));
    };

  }])
;
