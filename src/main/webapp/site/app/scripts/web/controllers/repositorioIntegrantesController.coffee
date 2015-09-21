angular.module('emailOverviewWebApp.controllers')
.controller 'RepositorioIntegrantesController',['$scope', '$location', '$filter' , '$stateParams', '$timeout', '$http', 'urlConfig', 'time', '$state', 'integrantes', 'RepositorioService', 'Feedback',
($scope, $location, $filter, $stateParams, $timeout, $http, urlConfig, time, $state, integrantes, RepositorioService, Feedback)->

  $scope.time = time
  $scope.integrantes = integrantes

  $scope.btnDisabled = false


  $scope.getFiltered = -> 
    _.difference integrantes, $scope.time 

  $scope.adicionarIntegrante = () ->
    unless _.isEmpty $scope.integrante
      for integrante in $scope.integrante
        $scope.time.push integrante
      $scope.integrante = undefined
      $scope.integrantes = do $scope.getFiltered

  $scope.excluirIntegrante = (index) ->
    $scope.time.splice index, 1
    $scope.integrantes = do $scope.getFiltered

  $scope.salvarTime = ->
    $scope.btnDisabled = true
    arrayIntegrante = []

    for i in $scope.time
      arrayIntegrante.push i.id

    RepositorioService.saveIntegrantes($stateParams.id, arrayIntegrante).then (result) ->
      $scope.time = result.data
      Feedback.success 'Salvo com sucesso.'
      $scope.btnDisabled = false
    (error) ->
      Feedback.fail 'Erro ao salvar os integrantes.'
      $scope.btnDisabled = false

]
