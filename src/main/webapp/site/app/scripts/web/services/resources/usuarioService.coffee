angular.module('emailOverviewWebApp.services')
.service('UsuarioService', [
	'$rootScope', '$http', 'httpUtils','urlConfig'
	($rootScope, $http, httpUtils, urlConfig) ->

			user = {}

			login: (params)->
				$http
					url: "#{urlConfig.baseUrl}/login/authenticate",
					method: "POST"
					data: $.param params
					headers:
						"Content-Type": "application/x-www-form-urlencoded"

			session: -> $http.get "#{urlConfig.baseUrl}/usuario"

			setUser: (session)->
				user = session

			getUser: ()->
				user or null

			checkSession: ()->
				that = @
				@session().then (result)->
					that.setUser result.data
				, (error)->
					if error.status is 500
						$rootScope.$emit 'sessionFailed'

			atualizaUsuario: (dadosPessoais, id) ->
				$http
					url 	: "#{urlConfig.baseUrl}/usuario/"+id
					method: "PUT"
					data: dadosPessoais
					headers:
						"Content-Type": "application/json"
	])
