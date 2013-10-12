'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('myApp.services', []).
	value('version', '0.1')
	.factory('ajaxService', function($http) {
		var ajaxService = {
			async: function( domain, params ) {
        params.callback = 'JSON_CALLBACK';
				var promise = $http({method: 'JSONP', url: 'http://eiffel.itba.edu.ar/hci/service3/'+domain+'.groovy', params: params })
				.success(function(data) {
					console.log(data);
                	return data;
            	})
            	.error(function(data, status, headers, config) {
                	alert("ERROR: Could not get data.");
            	});
				return promise;
			}
		};
		return ajaxService;
	});
