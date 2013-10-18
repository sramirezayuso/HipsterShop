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
      cs.remove('user.username');
      rt.$emit('refreshUser');
      as.async('Account', { method: 'SignOut' }).then(function(response) {});
    }
  }])

  .controller('PageNavCtrl', ['$location', '$scope',  '$rootScope', '$routeParams', function(lc, sc, rc, rp){
      sc.$on('$routeChangeSuccess', function (ev, current, prev) {
        sc.gender = current.params.gender;
       });

      sc.search = function() {
        lc.path('products').search('search', sc.searchTerm );
        lc.path('products').search('categoryId', 0);
        lc.path('products').search('subcategoryId', 0);
        rc.$emit('productsChange', rp.gender, 0, 0, sc.searchTerm);
      }
      sc.changeGender = function(gender){
        sc.gender = gender;
        lc.path('products').search('search', '');
        lc.path('products').search('categoryId', 0);
        lc.path('products').search('subcategoryId', 0);
        lc.path('products').search('gender', gender);

        rc.$emit('productsChange', gender, 0, 0, "");
        rc.$emit('updateCategories', gender);
      }
  }])

  .controller('HomeCtrl', function($scope, ajaxService){
        var sc = $scope;
		var div = 12, columns = 4;
		var lim = (2*div)/columns;
		
		ajaxService.async('Catalog', {method: 'GetAllSubcategories', id: 1} ).then(function(response) {
			var iM = 0, iH = 0;
			sc.categories1M = [ ];
			sc.categories1H = [ ];
			
			for(var i = 0; iM < lim || iH < lim; i++) {
				var each = response.data.subcategories[i];
				
				if(genreInCategory('Femenino', each) && iM++ < lim)
					sc.categories1M.push({name: each.name, categoryId: 1, subcategoryId: each.id});
					
				if(genreInCategory('Masculino', each) && iH++ < lim)
					sc.categories1H.push({name: each.name, categoryId: 1, subcategoryId: each.id});
			}
		});
		
		ajaxService.async('Catalog', {method: 'GetAllSubcategories', id: 2} ).then(function(response) {
			var iM = 0, iH = 0;
			sc.categories2M = [ ];
			sc.categories2H = [ ];
			
			for(var i = 0; iM < lim || iH < lim; i++) {
				var each = response.data.subcategories[i];
				
				if(genreInCategory('Femenino', each) && iM++ < lim)
					sc.categories2M.push({name: each.name, categoryId: 2, subcategoryId: each.id});
				
				if(genreInCategory('Masculino', each) && iH++ < lim)
					sc.categories2H.push({name: each.name, categoryId: 2, subcategoryId: each.id});
			}
		});
		
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

		sc.sections = [ ];

		var filt = [ { "id": 5, "value": "Oferta" } ];
        ajaxService.async('Catalog', {method: 'GetAllProducts', filters: filt} ).then(function(response) {
            sc.sections[1] = {name: "Ofertas", products: response.data.products.slice(0,4)};
        });

		var filt = [ { "id": 6, "value": "Nuevo" } ];
        ajaxService.async('Catalog', {method: 'GetAllProducts', filters: filt} ).then(function(response) {
            sc.sections[0] = {name: "Productos nuevos", products: response.data.products.slice(0,4)};
        });

  })

  .controller('ProductsCtrl', ['$scope', '$routeParams', 'ajaxService', '$location', '$rootScope', function(sc, rp, as, lc, rs) {
    sc.order = "marca";

    function addGenderFilter(gender, filters){
      var filters = [ ];
      sc.breadcrumb = [ ];

      switch(gender) {
        case "f" : filters.push({"id":1,"value":"Femenino"});
				   sc.header = "header-image-women";
				   sc.breadcrumb.push({url:"#/products?gender=f", name:"Mujeres"}); break;
        case "m" : filters.push({"id":1,"value":"Masculino"});
				   sc.header = "header-image-men";
				   sc.breadcrumb.push({url:"#/products?gender=m", name:"Hombres"}); break;
      }
      return filters;
    }

    function loadSubcategories(category, filters) {
      var subcategories = [ ];
      as.async('Catalog', {method: 'GetAllSubcategories', id: category.id, filters: filters}).then(function(response) {
        response.data.subcategories.forEach(function(subcategory) {
          var subUrl = '#/products?gender=' + rp.gender + '&categoryId=' + category.id + '&subcategoryId=' + subcategory.id;
          subcategories.push({ id: subcategory.id, title: subcategory.name, active: rp.subcategoryId == subcategory.id, url: subUrl});

          if(rp.subcategoryId == subcategory.id) {
            sc.breadcrumb.push({url:subUrl, name:subcategory.name});
          }
        });
        sc.breadcrumbLast = sc.breadcrumb.pop();
      });
      return subcategories;
    }

    function showProduct(product) {
      var brand = product.attributes.filter(function(attr) { return attr.id == 9; })[0].values[0];
      sc.products.push({ id: product.id, title: product.name, price: product.price, brand: brand, imageUrl: product.imageUrl[0]});
    }

    rs.$on('updateCategories', function(ev, gender) {
      sc.categories = [];
      var categoryFilters = addGenderFilter(gender, []);
      as.async('Catalog', {method: 'GetAllCategories', filters: categoryFilters}).then(function(response) {
        response.data.categories.forEach(function(category) {
          var url = '#/products?gender=' + rp.gender + '&categoryId=' + category.id;
          var active = category.id == rp.categoryId;
          var subcategories = [];

          if (active) {
            subcategories = loadSubcategories(category, categoryFilters);
            sc.breadcrumb.push({url:url, name:category.name});
          }

          sc.categories.push({ id: category.id, title: category.name, active: active, url: url, subcategories: subcategories});

        });

        if(sc.breadcrumb.length == 1){
          sc.breadcrumbLast = sc.breadcrumb.pop();
        }
      });
    });

    rs.$emit('updateCategories');

    sc.changeProductsCategory = function(category) {
      rs.$emit('productsChange', rp.gender, category.id, 0, "");
      sc.categories.forEach(function(cat){ cat.active = false; cat.subcategories.forEach(function(subcat){ subcat.active = false;});});
      category.active = true;
      lc.search('categoryId', category.id);
      lc.search('subcategoryId', 0);
      lc.search('search', '');
      if (category.subcategories.length == 0) {
        category.subcategories = loadSubcategories(category, [])
      }
    }

    sc.changeProductsSubcategory = function(category, subcategory) {
      lc.search('subcategoryId', subcategory.id);
      category.subcategories.forEach(function(subcat){subcat.active = false;});

      subcategory.active = true;
      rs.$emit('productsChange', rp.gender, rp.categoryId, subcategory.id, "");
    }

    sc.changeOrder = function(){
      rs.$emit('productsChange', rp.gender, rp.categoryId || 0, rp.subcategoryId || 0, rp.search || "");
    }

    // This search depends if there is a category, or a subcategory, or a search by name
    rs.$on('productsChange', function(ev, gender, categoryId, subcategoryId, search) {
      sc.products = [ ];
      var productFilters = addGenderFilter(gender, []);

      if (search.length > 0) {
        sc.categories.forEach(function(cat){cat.active=false;});
        as.async('Catalog', {method: 'GetProductsByName', name: search, filters: productFilters, sort_key: sc.order, page_size: 12}).then(function(response) {
          response.data.products.forEach(showProduct);
        });
      } else if (subcategoryId != 0) {
        as.async('Catalog', {method: 'GetProductsBySubcategoryId', id: subcategoryId, filters: productFilters, sort_key: sc.order, page_size: 12}).then(function(response) {
          response.data.products.forEach(showProduct);
        });
      } else if (categoryId != 0) {
        as.async('Catalog', {method: 'GetProductsByCategoryId', id: categoryId, filters: productFilters, sort_key: sc.order, page_size: 12}).then(function(response) {
          response.data.products.forEach(showProduct);
        });
      } else {
        // Show all products
        as.async('Catalog', {method: 'GetAllProducts', filters: productFilters, sort_key: sc.order, page_size: 12}).then(function(response) {
          response.data.products.forEach(showProduct);
        });
      }
    });

    rs.$emit('productsChange', rp.gender, rp.categoryId, rp.subcategoryId || 0, rp.search || "");
  }])

  .controller('ProductCtrl', ['$scope', '$routeParams', '$cookieStore', 'ajaxService', function(sc, rp, cs, as) {

    sc.quantity = 1;

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
          case 7: sc.product.size = att[i].values; break;
          case 1: sc.product.genre = att[i].values[0]=='Femenino'?{id:'f', name:'Mujeres'}:{id:'m', name:'Hombres'}; break;
        }
      }

	  sc.breadcrumb = [];
	  var url = '#/products?gender=' + sc.product.genre.id;
	  sc.breadcrumb.push({name: sc.product.genre.name, url: url});
	  url += '&categoryId=' + sc.product.category.id;
  	  sc.breadcrumb.push({name: sc.product.category.name, url: url});
	  url += '&subcategoryId=' + sc.product.subcategory.id;
  	  sc.breadcrumb.push({name: sc.product.subcategory.name, url: url});
    })

    sc.addToCart = function() {
      as.async('Account', {method: 'GetPreferences', username: cs.get('user.username'), authentication_token: cs.get('authToken')} ).then(function(response) {
        as.async('Order', {method: 'AddItemToOrder', username: cs.get('user.username'), authentication_token: cs.get('authToken'), order_item: {order: {id: JSON.parse(response.data.preferences).cartId}, product: {id: sc.product.id}, quantity: sc.quantity}} ).then(function(ans) {
		  if("error" in ans.data)
		    sc.error = true;
		  else
			sc.showMsgCart = true;
        });
      });
    }

    sc.addToWishlist = function() {
      as.async('Account', {method: 'GetPreferences', username: cs.get('user.username'), authentication_token: cs.get('authToken')} ).then(function(response) {
        as.async('Order', {method: 'AddItemToOrder', username: cs.get('user.username'), authentication_token: cs.get('authToken'), order_item: {order: {id: JSON.parse(response.data.preferences).wishId}, product: {id: sc.product.id}, quantity: sc.quantity}} ).then(function(ans) {
		  if("error" in ans.data)
		    sc.error = true;
		  else
			sc.showMsgWishlist = true;
        });
      });
    }

  }])

  .controller('CartCtrl', function($scope, $location, $cookieStore, ajaxService) {

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

    $scope.products = [];
    ajaxService.async('Account', {method: 'GetPreferences', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken')} ).then(function(response) {
      ajaxService.async('Order', {method: 'GetOrderById', username: $cookieStore.get('user.username'), id: JSON.parse(response.data.preferences).cartId, authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
        $scope.products = response.data.order.items;
      });
    });


    $scope.runningTotal = function(){
      var runningTotal = 0;
      angular.forEach($scope.products, function(product, index){
        runningTotal += product.price * product.quantity;
      });
      return runningTotal;
    };

    $scope.remove = function( idx ) {
      ajaxService.async('Order', {method: 'RemoveItemFromOrder', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken'), id: $scope.products[idx].id } ).then(function(response) {
      });
      $scope.products.splice(idx, 1);
    };

    $scope.go = function ( path ) {
      $location.path( path );
    };

  })

  .controller('WishlistCtrl', function($scope, $location, $cookieStore, ajaxService) {

    /*ajaxService.async('Account', {method: 'GetPreferences', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken')} ).then(function(response) {
      $scope.pref = JSON.parse(response.data.preferences);
      ajaxService.async('Order', {method: 'CreateOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
        $scope.pref.wishId = response.data.order.id;
        ajaxService.async('Account', {method: 'UpdatePreferences', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken'), value: JSON.stringify($scope.pref)} ).then(function(response) {
        });
      });
    });*/

    $scope.products = [];
    ajaxService.async('Account', {method: 'GetPreferences', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken')} ).then(function(response) {
      $scope.cartId = JSON.parse(response.data.preferences).cartId;
      ajaxService.async('Order', {method: 'GetOrderById', username: $cookieStore.get('user.username'), id: JSON.parse(response.data.preferences).wishId, authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
        $scope.products = response.data.order.items;
      });
    });


    $scope.runningTotal = function(){
      var runningTotal = 0;
      angular.forEach($scope.products, function(product, index){
        runningTotal += product.price * product.quantity;
      });
      return runningTotal;
    };

    $scope.remove = function( idx ) {
      ajaxService.async('Order', {method: 'RemoveItemFromOrder', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken'), id: $scope.products[idx].id } ).then(function(response) {
      });
      $scope.products.splice(idx, 1);
    };

    $scope.addToCart = function( idx ) {
      ajaxService.async('Order', {method: 'AddItemToOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), order_item: {order: {id: $scope.cartId}, product: {id: $scope.products[idx].product.id}, quantity: $scope.products[idx].quantity}} ).then(function(response) {
        ajaxService.async('Order', {method: 'RemoveItemFromOrder', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken'), id: $scope.products[idx].id } ).then(function(response) {
          $scope.products.splice(idx, 1);
        });
      });
    };

    $scope.go = function ( path ) {
      $location.path( path );
    };

  })

  .controller('OrdersCtrl', function($scope, $location, $cookieStore, ajaxService) {

    ajaxService.async('Account', {method: 'GetPreferences', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken')} ).then(function(response) {
      $scope.preferences = JSON.parse(response.data.preferences);
      $scope.ordernumbers = $scope.preferences.orders;
      $scope.orders = [];
      angular.forEach($scope.ordernumbers,function(number, index) {
        ajaxService.async('Order', {method: 'GetOrderById', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken'), id: number}).then(function(response) {
          $scope.orders.push(response.data.order);
        });
      })
    });

    $scope.getStatus = function( statusCode ) {
      switch( statusCode ) {
        case '1':
          return 'Creado';
        case '2':
          return 'Confirmado';
        case '3':
          return 'Envíado';
        case '4':
          return 'Entregado';
        default:
      }
    };

    $scope.goOrder = function ( orderno ) {
      $location.path( '/orders/' + orderno );
    };

    $scope.runningTotal = function(orderno){
      var runningTotal = 0;
      angular.forEach($scope.orders, function(order, index){
        if (order.orderno == orderno) {
          angular.forEach(order.products, function(product, index){
            runningTotal += product.price;
          })
        };
      });
      return runningTotal;
    };
  })


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
            cs.put('user.username', response.data.account.username);
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
              cs.put('user.username', response.data.account.username);
              cs.put('user.firstName', response.data.account.firstName);
              console.log('new cookie authToken:' + response.data.authenticationToken);
              as.async('Order', {method: 'CreateOrder', username: cs.get('user.username'), authentication_token: cs.get('authToken')} ).then(function(response) {
                sc.wishlist = response.data.order.id;
                as.async('Order', {method: 'CreateOrder', username: cs.get('user.username'), authentication_token: cs.get('authToken')} ).then(function(response) {
                  as.async('Account', {method: 'UpdatePreferences', username: cs.get('user.username'), authentication_token: cs.get('authToken'), value: JSON.stringify({cartId: response.data.order.id, wishId: sc.wishlist, orders: []}) } ).then(function(response) {
                    rt.$emit('refreshUser');
                    lc.path('#products')
                  });
                });
              });
            }
          });
        }
      });
    }

}])
.controller('OrderCtrl', function($scope, $routeParams, ajaxService) {

    $scope.orderno = $routeParams.orderno;
    $scope.products = [];

    ajaxService.async('Account', {method: 'SignIn', username: 'MattHarvey', password: 'nymetsharvey'} ).then(function(response) {
      $scope.authToken = response.data.authenticationToken;
      ajaxService.async('Order', {method: 'GetOrderById', username: 'MattHarvey', id: $routeParams.orderno, authentication_token: $scope.authToken} ).then(function(response) {
        console.log(response);
        $scope.products = response.data.order.items;
      });
    });

    $scope.runningTotal = function(){
      var runningTotal = 0;
      angular.forEach($scope.products, function(product, index){
        runningTotal += product.price * product.quantity;
      });
      return runningTotal;
    };

  })

  .controller('CheckoutCtrl', function($scope, $cookieStore, $q, ajaxService) {

    $scope.paymentMethod = 'cash';
    $scope.cardType = "amex";
    $scope.currentAddr = {};
    $scope.currentCard = {};
    $scope.addresses = [];
    $scope.cards = [];
    $scope.products = [];
    $scope.isAddressSaved = false;
    $scope.isCardSaved = false;



    ajaxService.async('Account', {method: 'GetPreferences', username: $cookieStore.get('user.username'), authentication_token:$cookieStore.get('authToken')} ).then(function(response) {
      $scope.preferences = JSON.parse(response.data.preferences)
      ajaxService.async('Order', {method: 'GetOrderById', username: $cookieStore.get('user.username'), id: JSON.parse(response.data.preferences).cartId, authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
        $scope.products = response.data.order.items;
      });
    });

    ajaxService.async('Account', {method: 'GetAllAddresses', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
      $scope.addresses = response.data.addresses;
    });
    ajaxService.async('Account', {method: 'GetAllCreditCards', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
      $scope.cards = response.data.creditCards;
    });

    $scope.runningTotal = function(){
      var runningTotal = 0;
      angular.forEach($scope.products, function(product, index){
        runningTotal += (product.price * product.quantity);
      });
      return runningTotal;
    };

    $scope.loadSavedAddress = function( idx ) {
      $scope.currentAddr = JSON.parse(JSON.stringify($scope.addresses[idx]));
      $scope.isAddressSaved = true;
    };

    $scope.loadSavedCard = function( idx ) {
      $scope.currentCard = JSON.parse(JSON.stringify($scope.cards[idx]));
      $scope.isCardSaved = true;
    };

    $scope.changeCardState = function( newState ) {
      $scope.isCardSaved = newState;
    }

    $scope.changeAddressState = function( newState ) {
      $scope.isAddressSaved = newState;
    }

    $scope.checkout = function() {
      if ($scope.paymentMethod == 'card') {
        if ($scope.isAddressSaved && $scope.isCardSaved) {
          ajaxService.async('Order', {method: 'ConfirmOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), order: {id: $scope.preferences.cartId, address:{id: $scope.currentAddr.id}, creditCard: {id: $scope.currentCard.id}}} ).then(function(response) {
            ajaxService.async('Order', {method: 'CreateOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
              $scope.preferences.orders.push($scope.preferences.cartId);
              $scope.preferences.cartId = response.data.order.id;
              ajaxService.async('Account', {method: 'UpdatePreferences', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), value: JSON.stringify($scope.preferences)} ).then(function(response) {});
            });
          });
        } else if (!$scope.isAddressSaved && $scope.isCardSaved){
          ajaxService.async('Account', {method: 'CreateAddress', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), address: $scope.currentAddr }).then(function(response) {
            ajaxService.async('Order', {method: 'ConfirmOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), order: {id: $scope.preferences.cartId, address:{id: response.data.address.id}, creditCard: {id: $scope.currentCard.id}}} ).then(function(response) {
              ajaxService.async('Order', {method: 'CreateOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
                if($scope.addresses.length > 2)
                  ajaxService.async('Account', {method: 'DeleteAddress', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), id: $scope.addresses[0].id} ).then(function(response) {});
                $scope.preferences.orders.push($scope.preferences.cartId);
                $scope.preferences.cartId = response.data.order.id;
                ajaxService.async('Account', {method: 'UpdatePreferences', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), value: JSON.stringify($scope.preferences)} ).then(function(response) {});
              });
            });
          });
        } else if ($scope.isAddressSaved && !$scope.isCardSaved){
          ajaxService.async('Account', {method: 'CreateCreditCard', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), credit_card: $scope.currentCard }).then(function(response) {
            ajaxService.async('Order', {method: 'ConfirmOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), order: {id: $scope.preferences.cartId, address:{id: $scope.currentAddr.id}, creditCard: {id: response.data.creditCard.id}}} ).then(function(response) {
              ajaxService.async('Order', {method: 'CreateOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
                if($scope.cards.length > 2)
                  ajaxService.async('Account', {method: 'DeleteCreditCard', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), id: $scope.cards[0].id} ).then(function(response) {});
                $scope.preferences.orders.push($scope.preferences.cartId);
                $scope.preferences.cartId = response.data.order.id;
                ajaxService.async('Account', {method: 'UpdatePreferences', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), value: JSON.stringify($scope.preferences)} ).then(function(response) {});
              });
            });
          });
        } else {
          ajaxService.async('Account', {method: 'CreateCreditCard', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), credit_card: $scope.currentCard.id }).then(function(response) {
            $scope.newCard = response.data.creditCard.id
            ajaxService.async('Account', {method: 'CreateAddress', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), address: $scope.currentAddr } ).then(function(response) {
              ajaxService.async('Order', {method: 'ConfirmOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), order: {id: $scope.preferences.cartId, address:{id: response.data.address.id}, creditCard: {id: $scope.newCard}}} ).then(function(response) {
                ajaxService.async('Order', {method: 'CreateOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
                  if($scope.cards.length > 2)
                    ajaxService.async('Account', {method: 'DeleteCreditCard', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), id: $scope.cards[0].id} ).then(function(response) {});
                  if($scope.addresses.length > 2)
                    ajaxService.async('Account', {method: 'DeleteAddress', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), id: $scope.addresses[0].id} ).then(function(response) {});
                  $scope.preferences.orders.push($scope.preferences.cartId);
                  $scope.preferences.cartId = response.data.order.id;
                  ajaxService.async('Account', {method: 'UpdatePreferences', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), value: JSON.stringify($scope.preferences)} ).then(function(response) {});
                });
              });
            });
          });
        }
      } else if ($scope.paymentMethod == 'cash') {
        if($scope.isAddressSaved){
          ajaxService.async('Order', {method: 'ConfirmOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), order: {id: $scope.preferences.cartId, address:{id: $scope.currentAddr.id}}} ).then(function(response) {
            ajaxService.async('Order', {method: 'CreateOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
              $scope.preferences.orders.push($scope.preferences.cartId);
              $scope.preferences.cartId = response.data.order.id;
              ajaxService.async('Account', {method: 'UpdatePreferences', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), value: JSON.stringify($scope.preferences)} ).then(function(response) {});
            });
          });
        } else {
          ajaxService.async('Account', {method: 'CreateAddress', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), address: $scope.currentAddr} ).then(function(response) {
            ajaxService.async('Order', {method: 'ConfirmOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), order: {id: $scope.preferences.cartId, address:{id: response.data.address.id}}} ).then(function(response) {
              ajaxService.async('Order', {method: 'CreateOrder', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken')} ).then(function(response) {
                if($scope.addresses.length > 2)
                  ajaxService.async('Account', {method: 'DeleteAddress', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), id: $scope.addresses[0].id} ).then(function(response) {});
                $scope.preferences.orders.push($scope.preferences.cartId);
                $scope.preferences.cartId = response.data.order.id;
                ajaxService.async('Account', {method: 'UpdatePreferences', username: $cookieStore.get('user.username'), authentication_token: $cookieStore.get('authToken'), value: JSON.stringify($scope.preferences)} ).then(function(response) {});
              });
            });
          });
        }
      }
    };
  });


