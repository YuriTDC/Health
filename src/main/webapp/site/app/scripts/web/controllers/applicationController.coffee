angular.module "emailOverviewWebApp.controllers"


.controller "ApplicationController", [
	'$scope', '$rootScope', '$state', 'UsuarioService', 'Feedback', '$log', '$window', '$location'
	($scope, $rootScope, $state, UsuarioService, Feedback, $log, $window, $location)->

		$rootScope.$on "loginRequired", ->
			if $state.current.name isnt 'web.login'
				$state.go "web.login"
				return

		$rootScope.$on "sessionFailed", ->
			$log.error "Sessão não autorizada"


		###$scope.checkSession = ->
			UsuarioService.session().then (result)->
				UsuarioService.setUser result.data
				$rootScope.isLogado = true

		do $scope.checkSession###

		return

]
