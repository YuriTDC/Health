angular.module('emailOverviewWebApp.controllers')
.controller 'CriarDadosProjetoController',['$scope', '$location', '$timeout', '$http', 'urlConfig', '$state', 'DadosProjetoService', 'Feedback', '$stateParams', 'dados', 'resolveRepositorios'
($scope, $location, $timeout, $http, urlConfig, $state, DadosProjetoService, Feedback, $stateParams, dados, resolveRepositorios)->

  $scope.saved = false
  $scope.dadosProjeto = {
    dataInicio: '',
    dataTermino: '',
    status: '',
    repositorioId: $stateParams.id
  }

  $scope.btnDisabled = false
  $scope.tipoForm = 'Dados Projeto'

  _.extend $scope.dadosProjeto, dados

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

  if $scope.dadosProjeto.status
    $scope.saved = true
  $scope.save = (dadosProjeto) ->
    $scope.saved = true
    $scope.btnDisabled = true
    DadosProjetoService.create($scope.dadosProjeto.id, _.omit($scope.dadosProjeto, 'id')).then (result) ->
      $scope.btnDisabled = false
      Feedback.success 'Dados salvos com sucesso.'
      $state.go 'web.projeto.gerenciar.dados'
    (error) ->
      Feedback.fail 'Falha ao salvar dados.'
      $scope.btnDisabled = false

  $scope.update = (dadosProjeto) ->
    $scope.saved = true
    $scope.btnDisabled = true
    DadosProjetoService.update($scope.dadosProjeto.id, _.omit($scope.dadosProjeto, 'id')).then (result) ->
      $scope.btnDisabled = false
      Feedback.success 'Dados atualizados com sucesso.'
      $state.go 'web.projeto.gerenciar.dados'
    (error) ->
      Feedback.fail 'Falha ao salvar dados.'
      $scope.btnDisabled = false

]
