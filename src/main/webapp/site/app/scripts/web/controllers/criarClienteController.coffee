angular.module('emailOverviewWebApp.controllers')
.controller 'CriarClienteController',['$scope', '$location', '$timeout', '$http', 'urlConfig', '$state', 'ClienteService', 'Feedback',
($scope, $location, $timeout, $http, urlConfig, $state, ClienteService, Feedback)->

  $scope.cliente = {
    nome: '',
    email: ''
  }

  $scope.btnDisabled = false
  $scope.tipoForm = 'NOVO CLIENTE'

  $scope.save = (cliente) ->
    $scope.btnDisabled = true
    ClienteService.create($scope.cliente).then (result) ->
      $scope.btnDisabled = false
      $state.go 'web.cliente.listar'
      Feedback.success 'Cliente salvo com sucesso.'
    (error) ->
      Feedback.fail 'Falha ao salvar cliente.'
      $scope.btnDisabled = false


]
