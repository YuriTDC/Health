angular.module('emailOverviewWebApp.controllers')
.controller 'EntregaSprintController', ['$scope', '$location', '$timeout', '$http', 'urlConfig', 'entregaSprintResolve', '$state', 'EntregaSprintService', 'Feedback',
($scope, $location, $timeout, $http, urlConfig, entregaSprintResolve, $state, EntregaSprintService, Feedback)->

  $scope.btnDisabled = false

  $scope.entregaSprintResolve = entregaSprintResolve?.data

  $scope.novaEntregaSprint = ->
    $state.go('web.projeto.gerenciar.fases.sprint.criar')

  $scope.editarEntregaSprint = (entregaSprint) ->
    $state.go('web.entregaSprint.editar', {'id': entregaSprint.id })

  $scope.excluirEntregaSprint = (entregaSprint) ->
    $scope.btnDisabled = true
    if confirm('Deseja excluir?')
      EntregaSprintService.excluir(entregaSprint.id).then( (result) ->
        Feedback.success 'Excluido com sucesso.'
        $state.reload()
        $scope.btnDisabled = false
      (error) ->
        $scope.btnDisabled = false
        Feedback.fail "Erro ao excluir entrega sprint. #{error.data.message}"
      )

  ]

