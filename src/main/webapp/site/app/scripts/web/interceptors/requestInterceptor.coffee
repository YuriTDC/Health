angular.module("emailOverviewWebApp.interceptors")

.factory 'requestInterceptor', ['$rootScope','$q', '$log', 'Feedback', ($rootScope, $q, $log, Feedback)->

	noCache = (url)-> "#{url}?_=#{Math.random()}"

	checkSession = (request)->
		if request.status is 401
			Feedback.fail "Não autenticado"
			$rootScope.$emit "loginRequired"
			$rootScope.isLogado = false

		if request.status is 0
			$rootScope.$emit "loginRequired"
			$rootScope.isLogado = false

	setRequestHeader = (config)->
		config.headers['X-HTTP-Method-Override'] = config.method if config.method is 'DELETE'
#    TODO: SE O LULU VIER COM PATIFARIA DE _METHOD DEPOIS, EU MANDO ELE ENFIAR NO CÓDIGO
		###if config.method is 'DELETE'
			config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
			if !config.data
				config.data =
					_method: config.method
				config.data = $.param config.data
			config.method = 'POST'###
		return

	request: (config)->
		setRequestHeader config

		config or $q.when config

	requestError: (rejection)->
		$q.reject rejection

	response: (response)->
		response or $q.when response

	responseError: (rejection)->

		checkSession rejection

#		$log.error '[BERTIOGA-WEB]Response error from request    : ', rejection.data
		$log.error '[BERTIOGA-WEB]Status Code                    : ', rejection.status
		$log.error '[BERTIOGA-WEB]Status Text                    : ', rejection.statusText
		msg = """
				Erro: #{rejection.status}, #{rejection.statusText}
			"""

#		Feedback.fail msg
		$q.reject rejection

]
