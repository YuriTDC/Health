angular.module('emailOverviewWebApp.controllers')
.controller 'FaseProjetoRepostiorioController',['$scope', '$location', '$stateParams', '$timeout', '$http', 'urlConfig', 'repositorios', '$state', 'listaRepositorios', 'FaseProjService', 'Feedback',
($scope, $location, $stateParams, $timeout, $http, urlConfig, repositorios, $state, listaRepositorios, RepositorioService, Feedback)->

  $scope.repositorios = repositorios
  $scope.listaRepositorios = listaRepositorios

  $scope.newRepositorio = {}
  $scope.btnDisabled = false

  $scope.adicionarEepositorio = (repositorios) ->
    unless _.isEmpty repositorio
      $scope.repositorios.push repositorio
      $scope.listaRepositorios = _.without $scope.listaRepositorios, repositorio

  $scope.excluirRepositorio = (repositorio) ->
    $scope.repositorios = _.without $scope.repositorios, repositorio
    $scope.listaRepositorios.push repositorio

  $scope.salvarRepositorios = ->
    $scope.btnDisabled = true
    arrayRepositorios = []

    for c in $scope.repositorios
      arrayRepositorios.push c.id

    RepositorioService.saveRepositorios($stateParams.id, arrayRepositorios).then (result) ->
      $scope.repositorios = result.data
      Feedback.success 'Salvo com sucesso.'
      $scope.btnDisabled = false
    (error) ->
      Feedback.fail 'Erro ao salvar os repositorios.'
      $scope.btnDisabled = false

]
