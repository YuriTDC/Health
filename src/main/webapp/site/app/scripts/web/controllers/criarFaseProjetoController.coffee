angular.module('emailOverviewWebApp.controllers')
.controller 'CriarFaseProjetoController',['$scope', '$location', '$timeout', '$http', 'urlConfig', '$state', 'FaseProjetoService', 'Feedback', '$stateParams'
($scope, $location, $timeout, $http, urlConfig, $state, FaseProjetoService, Feedback, $stateParams)->

  $scope.faseProjeto = {
    nome: '',
    status: '',
    repositorioId: $stateParams.id
  }

  $scope.allStatus = [
    {
      nome : "EM ANDAMENTO"
      status: "EM_ANDAMENTO"
    }] 

  $scope.btnDisabled = false
  $scope.tipoForm = 'Nova Fase Projeto'

  $scope.save = ->
    $scope.btnDisabled = true
    FaseProjetoService.create($scope.faseProjeto).then (result) ->
      $scope.btnDisabled = false
      Feedback.success 'Fase salva com sucesso.'
      $state.go 'web.projeto.gerenciar.fases.listar'
    (error) ->
      Feedback.fail 'Falha ao salvar faseProjeto.'
      $scope.btnDisabled = false

]
