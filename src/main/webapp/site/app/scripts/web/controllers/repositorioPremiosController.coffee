angular.module('emailOverviewWebApp.controllers')
.controller 'RepositorioPremiosController',['$scope', '$location', '$stateParams', '$timeout', '$http', 'urlConfig', 'premios', '$state', 'listapremios', 'RepositorioService', 'Feedback',
($scope, $location, $stateParams, $timeout, $http, urlConfig, premios, $state, listaPremios, RepositorioService, Feedback)->

  $scope.premios = premio
  $scope.listaPremios = listaPremios

  $scope.newPremio = {}
  $scope.btnDisabled = false

  $scope.adicionarPremio = (premio) ->
    unless _.isEmpty premio
      $scope.premios.push premio
      $scope.listaPremios = _.without $scope.listaPremios, premio

  $scope.excluirPremio = (premio) ->
    $scope.premios = _.without $scope.premios, premio
    $scope.listaPremios.push premio

  $scope.salvarPremio = ->
    $scope.btnDisabled = true
    arrayPremios = []

    for p in $scope.premios
      arrayPremios.push p.id

    RepositorioService.savePremios($stateParams.id, arrayPremios).then (result) ->
      $scope.premios = result.data
      Feedback.success 'Salvo com sucesso.'
      $scope.btnDisabled = false
    (error) ->
      Feedback.fail 'Erro ao salvar os premios.'
      $scope.btnDisabled = false

]
