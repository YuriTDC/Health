angular.module('emailOverviewWebApp.controllers')
.controller 'EditarPremioController',['$scope', '$location', '$timeout', '$http', 'urlConfig', 'premio', '$state', 'PremioService', 'Feedback',
($scope, $location, $timeout, $http, urlConfig, premio, $state, PremioService, Feedback)->

  $scope.premio = premio
  $scope.btnDisabled = false
  $scope.tipoForm = 'EDITAR PREMIO'

  $scope.save = ->
    $scope.btnDisabled = true
    promise = PremioService.editar($scope.premio.id, _.omit($scope.premio, 'id'))
    promise.success (result) ->
      $scope.btnDisabled = false
      Feedback.success 'Premio alterado com sucesso.'
      $state.go 'web.premio.listar'
    promise.error (error) ->
      Feedback.fail 'Falha ao salvar premio.'
      $scope.btnDisabled = false

  return @

]
