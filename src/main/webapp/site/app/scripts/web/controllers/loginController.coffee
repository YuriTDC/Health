angular.module('emailOverviewWebApp.controllers')
.controller 'LoginController',['$scope', '$rootScope','$state', '$stateParams', 'UsuarioService', 'Feedback'
($scope, $rootScope, $state, $stateParams, UsuarioService, Feedback)->

	$scope.form = {}

	$scope.entrar = ->
		UsuarioService.login($scope.form).then( (result) ->
			$rootScope.isLogado = true
			$state.go "web.home"
			UsuarioService.session().then( (result)->
				UsuarioService.setUser result.data
				$state.go "web.home"
			)
			(error) ->
				Feedback.fail 'Usuário ou senha incorretos.'
		)

	$scope.signup = ->
		alert 'Verificaremos seu usuario.'

]
