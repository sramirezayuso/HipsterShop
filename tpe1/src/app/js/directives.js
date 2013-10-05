'use strict';

/* Directives */


angular.module('myApp.directives', [])
.directive('appVersion', ['version', function(version) {
	return function(scope, elm, attrs) {
		elm.text(version);
	};
}])
.directive('animate', function(){
	return function(scope, elm, attrs) {
		setTimeout(function(){

		});
	};
})
.directive('remove', function(){
	return function(scope, elm, attrs) {
		elm.bind('click', function(e){
			e.preventDefault();
			elm.parent().addClass('cart-hide');
			setTimeout(function(){
				scope.$apply(function(){
					scope.$eval(attrs.remove);
				});
			}, 200);
		});
	};
});
