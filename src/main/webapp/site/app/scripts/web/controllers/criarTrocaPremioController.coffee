angular.module('emailOverviewWebApp.controllers')
.controller 'CriarTrocaPremioController',['$scope', '$location', '$timeout', '$http', 'urlConfig', '$state', 'TrocaPremioService', 'Feedback', 'premiosResolve', 'integrantesResolve', 'PremioService', 'IntegranteService',
($scope, $location, $timeout, $http, urlConfig, $state, TrocaPremioService, Feedback, premiosResolve, integrantesResolve, PremioService, IntegranteService)->

  $scope.trocaPremio = {
    integranteNome: '',
    premioNome: ''
  }

  $scope.integranteTroca = ''
  $scope.premioTroca = ''
  $scope.trocaId = ''

  $scope.attrs =
    premios : premiosResolve?.data or []
    integrantes : integrantesResolve?.data or []

  $scope.btnDisabled = false
  $scope.tipoForm = 'NOVA TROCA'

  $scope.save = (trocaPremio) ->
    promiseIntegrantes = IntegranteService.recuperar($scope.trocaPremio.integrante.id)
    promiseIntegrantes.success (data) ->
      $scope.integranteTroca = data
    promiseIntegrantes.error ->
      alert 'Erro Integrantes'

    promisePremios = PremioService.recuperar($scope.trocaPremio.premio.id)
    promisePremios.success (data) ->
      $scope.premioTroca = data
    promisePremios.error ->
      alert  'Erro Premios'

    if $scope.trocaPremio.integrante.sparksRestantes >= $scope.trocaPremio.premio.valor
      $scope.btnDisabled = true
      TrocaPremioService.create($scope.trocaPremio).then (result) ->
        $scope.btnDisabled = false
        Feedback.success 'Troca salva com sucesso.'
        $state.go 'web.trocaPremio.listar'
      (error) ->
        Feedback.fail 'Falha ao salvar troca.'
        $scope.btnDisabled = false
    else
      Feedback.fail 'Sparks insuficientes'
]
