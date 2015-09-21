angular.module('emailOverviewWebApp.controllers')
.controller 'EditarDadosProjetoController',['$scope', '$location', '$timeout', '$http', 'urlConfig', 'dadosProjeto', '$state', 'DadosProjetoService', 'Feedback', 'resolveRepositorios',
($scope, $location, $timeout, $http, urlConfig, dadosProjeto, $state, DadosProjetoService, Feedback, resolveRepositorios)->

	$scope.dadosProjeto = dadosProjeto
	$scope.btnDisabled = false
	$scope.tipoForm = 'EDITAR DADOS PROJETO'

	$scope.attrs =
		repositorios : resolveRepositorios?.data or []

	$scope.allStatus = [
		{
			nome : "EM ESPERA"
			status: "EM_ESPERA"
		}
		{
			nome : "EM ANDAMENTO"
			status: "EM_ANDAMENTO"
		}
		{
			nome : "FINALIZADO"
			status: "FINALIZADO"
		}] 
	
	$scope.save = ->
    $scope.btnDisabled = true
    DadosProjetoService.editar($scope.dadosProjeto.id, _.omit($scope.dadosProjeto, 'id')).then (result) ->
      $scope.btnDisabled = false
      Feedback.success 'Dados alterado com sucesso.'
  (error) ->
      Feedback.fail 'Falha ao salvar dados.'
      $scope.btnDisabled = false
		
]
