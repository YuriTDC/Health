angular.module('emailOverviewWebApp.controllers')
.controller 'IntegranteController',['$scope', '$location', '$timeout', '$http', 'urlConfig', 'integrantes', '$state', 'IntegranteService', 'Feedback', 'SweetAlert',
($scope, $location, $timeout, $http, urlConfig, integrantes, $state, IntegranteService, Feedback, SweetAlert)->

  $scope.integrantes = integrantes
  $scope.btnDisabled = false
  $scope.intes = {}
  $scope.intesTokens = {}
  $scope.intesRestantes = {}

  $scope.selectFilter = false

  $scope.pagination = ->
    $scope.viewby = 10
    $scope.totalItems = $scope.integrantes.length
    $scope.currentPage = 1
    $scope.itemsPerPage = $scope.viewby
    $scope.maxSize = 4

    $scope.setPage = (pageNo) ->
      $scope.currentPage = pageNo

    $scope.setItemsPerPage = (num) ->
      $scope.itemsPerPage = num
      $scope.currentPage = 1 

  $scope.recuperarTokens = ->
    IntegranteService.recuperarTokensIntegrante(int.id)
    promiseTokens.success (data)->
      $scope.intesTokens = data
      selectFilter = false
    promiseTokens.error ->
      alert 'Erro intesTokens'


  $scope.recuperarTokensRestantes = (int)->
    promiseRestantes = IntegranteService.tokensRestantes(int.id)
    promiseRestantes.success (data)->
      $scope.integrantes = data
    promiseRestantes.error ->
      alert 'Erro intesTokensRestantes'

  $scope.alterarIntegrante = (integrante) ->
    $state.go('web.integrante.editar', {'id': integrante.id})

  $scope.novoIntegrante = ->
    $state.go('web.integrante.criar')
  
  $scope.excluirIntegrante = (integrante)->
    SweetAlert.swal {
      title: 'Você tem certeza?'
      text: 'Não será possível desfazer essa operação!!'
      type: 'warning'
      showCancelButton: true
      closeOnConfirm: true
    },
    (isConfirmed)->
      if isConfirmed
        IntegranteService.excluir(integrante.id)
          .success (data)->
            SweetAlert.swal({title:"Item exluído!",text:"Operação realizada com sucesso!",type:"success"})
            $state.reload()
          .error (error)->
            SweetAlert.swal({title:"OPS!",text:error.message,type:"error"}) 
]
