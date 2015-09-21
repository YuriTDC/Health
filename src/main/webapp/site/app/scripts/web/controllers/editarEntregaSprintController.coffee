angular.module('emailOverviewWebApp.controllers')
.controller 'EditarEntregaSprintController',['$scope', '$location', '$timeout', '$http', 'urlConfig', '$state', 'EntregaSprintService', 'Feedback', 'entregaSprint', 'FaseProjetoService', 'RepositorioService', 'resolveTime',
($scope, $location, $timeout, $http, urlConfig, $state, EntregaSprintService, Feedback, entregaSprint, FaseProjetoService, RepositorioService, resolveTime)->

  $scope.entregaSprint = entregaSprint 

  $scope.changeFaseHandler = ->
    promise = RepositorioService.getTime($scope.entregaSprintId.fase.repositorioId)
    promise.success (data) ->
      $scope.entregaSprintId.time = data
    
    promise.error ->
      alert 'falhooou'

  $scope.allStatus = [
    {
      nome : "NAO ENTREGUE"
      entregue: "false"
    }
    {
      nome : "ENTREGUE"
      entregue: "true"
    }]

  $scope.btnDisabled = false
  $scope.tipoForm = 'EDITAR ENTREGA SPRINT'

  $scope.attrs = resolveTime

  $scope.save = ->
    $scope.btnDisabled = true
    EntregaSprintService.editar($scope.entregaSprint.id, _.omit($scope.entregaSprint, 'id')).then (result) ->
      $scope.btnDisabled = false
      $state.go 'web.projeto.gerenciar.fases.editar'
      Feedback.success 'Sprint alterado com sucesso.'
  (error) ->
      Feedback.fail 'Falha ao salvar fase.'
      $scope.btnDisabled = false
]
