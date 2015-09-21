angular.module('emailOverviewWebApp.controllers')
.controller 'CriarPremioController',['$scope', '$location', '$timeout', '$http', 'urlConfig', '$state', 'PremioService', 'Feedback',
($scope, $location, $timeout, $http, urlConfig, $state, PremioService, Feedback)->

  $scope.premio = {
    nome: '',
    valor: ''
  }

  $scope.btnDisabled = false
  $scope.tipoForm = 'NOVO PREMIO'

  $scope.save = (premio) ->
    $scope.btnDisabled = true

    promise = PremioService.create($scope.premio) 

    promise.success (result) ->
      $scope.btnDisabled = false
      Feedback.success 'Premio salvo com sucesso.'
      $state.go 'web.premio.listar'
    
    promise.error (error) ->
      Feedback.fail 'Falha ao salvar premio.'
      $scope.btnDisabled = false

]
