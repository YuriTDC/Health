'use strict'

###*
 # @ngdoc overview
 # @name emailOverviewWebApp config init
 # @description
 # # emailOverviewWebApp config init
 #
 # Main module of the application.
###
angular
.module('emailOverviewWebApp')
.run ['$rootScope', '$state', 'UsuarioService', '$modalStack', ($rootScope, $state, UsuarioService, $modalStack) ->

	$rootScope.isLogado = false

	clearModals = ->
		topModal = do $modalStack.getTop
		$modalStack.dismiss topModal.key if topModal


	checkLogin = (state, ev)->

		checkRoute = ->
			if state.restrict and !UsuarioService.getUser().id
				do ev.preventDefault
				$state.go "web.login", {
					redir: state.name
				}



		if _.isEmpty UsuarioService.getUser()
			UsuarioService.checkSession().then ->
				do checkRoute
				return
		else
			if state.restrict
				UsuarioService.checkSession().then ->
					do checkRoute
					return




	$rootScope.$on "$stateChangeError", (event, toState, toParams) ->
		do clearModals
		console.log( "$stateChangeError", event, toState, toParams)

	$rootScope.$on "$stateChangeStart", (event, toState, toParams, fromState, fromParams) ->
		do clearModals
		console.log( "$stateChangeStart", event, toState, toParams)

	$rootScope.$on "$stateChangeSuccess", (event, toState, toParams, fromState, fromParams) ->
		do clearModals
		$rootScope.$broadcast "closeWebSocketConnection" , {toState: toState, fromState: fromState}
		console.log( "$stateChangeSuccess", event, toState, toParams)


		checkLogin toState, event

]

