angular.module('emailOverviewWebApp.controllers')
.controller 'EditarClienteController',['$scope', '$location', '$timeout', '$http', 'urlConfig', 'cliente', '$state', 'ClienteService', 'Feedback',
($scope, $location, $timeout, $http, urlConfig, cliente, $state, ClienteService, Feedback)->

  $scope.cliente = cliente
  $scope.btnDisabled = false
  $scope.tipoForm = 'EDITAR CLIENTE'

  $scope.save = ->
    $scope.btnDisabled = true
    ClienteService.editar($scope.cliente.id, _.omit($scope.cliente, 'id')).then (result) ->
      $scope.btnDisabled = false
      Feedback.success 'Cliente alterado com sucesso.'
      $state.go 'web.cliente.listar'
    (error) ->
      Feedback.fail 'Falha ao salvar cliente.'
      $scope.btnDisabled = false

]
