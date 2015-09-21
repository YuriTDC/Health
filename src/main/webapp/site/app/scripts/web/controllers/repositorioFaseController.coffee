angular.module('emailOverviewWebApp.controllers')
.controller 'RepositorioColaboradoresController',['$scope', '$location', '$filter', '$routeProvider', '$locationProvider', '$stateParams', '$timeout', '$http', 'urlConfig', 'clientes', '$state', 'listaClientes', 'time', 'integrantes','RepositorioService', 'faseProjetoResolve','Feedback',
($scope, $location, $filter, $routeProvider, $locationProvider, $stateParams, $timeout, $http, urlConfig, clientes, $state, listaClientes, time, integrantes, RepositorioService, Feedback, faseProjetoResolve)->

  $scope.clientes = clientes
  $scope.listaClientes = listaClientes

  $scope.btnDisabled = false

  $scope.getFiltered = -> 
    _.difference listaClientes, $scope.clientes 

  $scope.adicionarCliente = () ->
    unless _.isEmpty $scope.cliente
      for cliente in $scope.cliente
        $scope.clientes.push cliente
      $scope.cliente = undefined
      $scope.listaClientes = do $scope.getFiltered

  $scope.excluirCliente = (index) ->
    $scope.clientes.splice index, 1
    $scope.listaClientes = do $scope.getFiltered

  $scope.salvarClientes = ->
    $scope.btnDisabled = true
    arrayClientes = []

    for c in $scope.clientes
      arrayClientes.push c.id

    RepositorioService.saveClientes($stateParams.id, arrayClientes).then (result) ->
      $scope.clientes = result.data
      $scope.btnDisabled = false

    arrayIntegrante = []

    for i in $scope.time
      arrayIntegrante.push i.id

    RepositorioService.saveIntegrantes($stateParams.id, arrayIntegrante).then (result) ->
      $scope.time = result.data
      Feedback.success 'Salvo com sucesso.'
      $scope.btnDisabled = false
    (error) ->
      Feedback.fail 'Erro ao salvar.'
      $scope.btnDisabled = false

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

  $scope.novaFaseProjeto = ->
    $state.go('web.projeto.gerenciar.fases.criar')

  $scope.faseProjetoResolve = faseProjetoResolve?.data

  $scope.selectedTab = $scope.tabs[0]

  $scope.setSelectedTab = (tab) ->
    $scope.selectedTab = tab

  $scope.tabsCtrl = -> 
    $scope.tabs = [
      {
        link: 'web.projeto.gerenciar.dados'
        label: 'Dados'
      }
      {
        link: 'web.projeto.gerenciar.colaboradores'
        label: 'Colaboradores'
      }
      {
        link: 'web.projeto.gerenciar.fases'
        label: 'Fases'
      }
    ]

  ($routeProvider, $locationProvider) ->
      $routeProvider.when('/dados',
        templateUrl: 'dados.html'
        controller: DadosCtrl)
      .when('/colaboradores',
        templateUrl: 'colaboradores.html'
        controller: ColaboradoresCtrl)
      .when('/fases',
        templateUrl: 'fases.html'
        controller: FasesCtrl)
      .otherwise redirectTo: '/dados'

      $locationProvider.html5Mode false



]
