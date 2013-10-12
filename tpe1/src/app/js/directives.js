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
})
.directive('uiIf', [function () {
  return {
    transclude: 'element',
    priority: 1000,
    terminal: true,
    restrict: 'A',
    compile: function (element, attr, transclude) {
      return function (scope, element, attr) {

        var childElement;
        var childScope;

        scope.$watch(attr['uiIf'], function (newValue) {
          if (childElement) {
            childElement.remove();
            childElement = undefined;
          }
          if (childScope) {
            childScope.$destroy();
            childScope = undefined;
          }

          if (newValue) {
            childScope = scope.$new();
            transclude(childScope, function (clone) {
              childElement = clone;
              element.after(clone);
            });
          }
        });
      };
    }
  };
}]);
