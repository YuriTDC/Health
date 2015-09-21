angular.module('emailOverviewWebApp.controllers')
.controller 'CriarEntregaSprintController',['$scope', '$location', '$timeout', '$http', 'urlConfig', '$state', 'EntregaSprintService', 'Feedback', 'RepositorioService', '$stateParams', 'resolveTime',
($scope, $location, $timeout, $http, urlConfig, $state, EntregaSprintService, Feedback, RepositorioService, $stateParams, resolveTime)->

	$scope.entregaSprint = {
		entregue: '',
		integrantes: '',
		dataEntrega: '',
		faseId: $stateParams.faseId
	}

	$scope.allStatus = [
		{
			nome : "NAO ENTREGUE"
			status: "false"
		}
		{
			nome : "ENTREGUE"
			status: "true"
		}]

	$scope.attrs = resolveTime

	$scope.btnDisabled = false
	$scope.tipoForm = 'NOVA ENTREGA'

	$scope.save = (entregaSprint) ->
		$scope.btnDisabled = true
		EntregaSprintService.create($scope.entregaSprint).then (result) ->
			$scope.btnDisabled = false
			Feedback.success 'Entrega salva com sucesso.'
			$state.go('web.projeto.gerenciar.fases.editar ', {'faseId' : $stateParams.faseId })
		(error) ->
			Feedback.fail 'Falha ao salvar entrega.'
			$scope.btnDisabled = false


]
