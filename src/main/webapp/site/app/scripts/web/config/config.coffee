'use strict'

###*
 # @ngdoc overview
 # @name emailOverviewWebApp config
 # @description
 # # emailOverviewWebApp config
 #
 # Main module of the application.
###
angular
.module('emailOverviewWebApp')

.config ['$httpProvider', ($httpProvider, Stomp)->
	$httpProvider.defaults.headers.common['Content-Type'] = 'application/x-www-form-urlencoded'
	#	$httpProvider.defaults.headers.post['Content-Type'] = 'application/json'
	$httpProvider.defaults.useXDomain = true
	delete $httpProvider.defaults.headers.common['X-Requested-With']
	$httpProvider.interceptors.push 'requestInterceptor'

	return

]
