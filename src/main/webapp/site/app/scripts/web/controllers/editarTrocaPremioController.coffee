angular.module('emailOverviewWebApp.controllers')
.controller 'EditarTrocaPremioController',['$scope', '$location', '$timeout', '$http', 'urlConfig', 'trocaPremio', '$state', 'TrocaPremioService', 'Feedback',
($scope, $location, $timeout, $http, urlConfig, trocaPremio, $state, TrocaPremioService, Feedback)->

  $scope.trocaPremio = trocaPremio
  $scope.btnDisabled = false
  $scope.tipoForm = 'EDITAR TROCA'

  $scope.save = ->
    $scope.btnDisabled = true
    TrocaPremioService.editar($scope.trocaPremio.id, _.omit($scope.trocaPremio, 'id')).then (result) ->
      $scope.btnDisabled = false
      Feedback.success 'Troca alterado com sucesso.'
      $state.go 'web.trocaPremio.list'
    (error) ->
      Feedback.fail 'Falha ao salvar troca.'
      $scope.btnDisabled = false

  return @
]
