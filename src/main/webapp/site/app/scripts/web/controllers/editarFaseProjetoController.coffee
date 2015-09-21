angular.module('emailOverviewWebApp.controllers')
.controller 'EditarFaseProjetoController',['$scope', '$location', '$timeout', '$stateParams', '$http', 'urlConfig', 'faseProjeto', 'entregaSprintResolve', '$state', 'FaseProjetoService', 'Feedback', 'resolveRepositorios', 'EntregaSprintService', 'SweetAlert'
($scope, $location, $timeout, $stateParams, $http, urlConfig, faseProjeto, entregaSprintResolve, $state, FaseProjetoService, Feedback, resolveRepositorios, EntregaSprintService, SweetAlert)->

	$scope.faseProjeto = faseProjeto
	$scope.btnDisabled = false
	$scope.tipoForm = 'EDITAR FASE PROJETO'

	$scope.attrs =
		repositorios : resolveRepositorios?.data or []

	$scope.allStatus = [
		{
			nome : "NAO ENTREGUE"
			status: "NAO_ENTREGUE"
		}
		{
			nome : "EM ANDAMENTO"
			status: "EM_ANDAMENTO"
		}
		{
			nome : "ENTREGUE"
			status: "ENTREGUE"
		}] 
	
	$scope.save = ->
		$scope.btnDisabled = true
		FaseProjetoService.editar($scope.faseProjeto.id, _.omit($scope.faseProjeto, 'id')).then (result) ->
			$scope.btnDisabled = false
			$state.go 'web.projeto.gerenciar.fases.listar'
			Feedback.success 'Fase alterada com sucesso.'
	(error) ->
			Feedback.fail 'Falha ao salvar fase.'
			$scope.btnDisabled = false

	$scope.entregaSprintResolve = entregaSprintResolve?.data

	$scope.novaEntregaSprint = ->
		$state.go('web.projeto.gerenciar.fases.sprint.criar', $stateParams)

	$scope.editarEntregaSprint = (entregaSprint) ->
		$state.go('web.projeto.gerenciar.fases.sprint.editar', {'faseId': faseProjeto.id,'entregaId': entregaSprint.id })

	$scope.excluirEntregaSprint = (entregaSprint) ->
    SweetAlert.swal {
      title: 'Você tem certeza?'
      text: 'Não será possível desfazer essa operação!!'
      type: 'warning'
      showCancelButton: true
      closeOnConfirm: true
    },
    (isConfirmed)->
      if isConfirmed
        EntregaSprintService.excluir(entregaSprint.id)
          .success (data)->
            SweetAlert.swal({title:"Item exluído!",text:"Operação realizada com sucesso!",type:"success"})
            $state.reload()
          .error (error)->
            SweetAlert.swal({title:"OPS!",text:error.message,type:"error"})  

		
		
]
