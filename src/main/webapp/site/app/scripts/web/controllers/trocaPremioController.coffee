angular.module('emailOverviewWebApp.controllers')
.controller 'TrocaPremioController', ['$scope', '$location', '$timeout', '$http', 'urlConfig', 'trocaPremioResolve', '$state', 'TrocaPremioService', 'Feedback', 'SweetAlert'
($scope, $location, $timeout, $http, urlConfig, trocaPremioResolve, $state, TrocaPremioService, Feedback, SweetAlert)->
 
  $scope.btnDisabled = false
  $scope.posicao = 0

  FILTRO_TROCA_ENUM = 
    TROCA: 'Todas trocas',
    INTEGRANTES: 'Integrantes sem premios',
    RANKING : 'Ranking de premios'

  $scope.FILTRO_TROCA_ENUM = FILTRO_TROCA_ENUM
  $scope.trocas = trocaPremioResolve?.data


  $scope.$watch 'selectFilter', ( newVal, oldVal ) ->
    if newVal is FILTRO_TROCA_ENUM.RANKING 
      promise = TrocaPremioService.rankPremio()
      promise.success (data) ->
        $scope.trocas = data

    else if newVal is  FILTRO_TROCA_ENUM.INTEGRANTES
      promise = TrocaPremioService.trocaSemPremio()
      promise.success (data) ->
        $scope.trocas = data  

    else if newVal is FILTRO_TROCA_ENUM.TROCA 
      $scope.trocas = trocaPremioResolve?.data


  $scope.novaTrocaPremio = ->
    $state.go('web.trocaPremio.criar')

  $scope.excluirTrocaPremio = (trocaPremio)->
    SweetAlert.swal {
      title: 'Você tem certeza?'
      text: 'Não será possível desfazer essa operação!!'
      type: 'warning'
      showCancelButton: true
      closeOnConfirm: true
    },
    (isConfirmed)->
      if isConfirmed
        TrocaPremioService.excluir(trocaPremio.id)
          .success (data)->
            SweetAlert.swal({title:"Item exluído!",text:"Operação realizada com sucesso!",type:"success"})
            $state.reload()
          .error (error)->
            SweetAlert.swal({title:"OPS!",text:error.message,type:"error"})   

  ]

