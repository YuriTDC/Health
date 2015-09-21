angular.module('emailOverviewWebApp.controllers')
.controller 'FaseProjetoController', ['$scope', '$location', '$timeout', '$http', 'urlConfig', 'faseProjetoResolve', '$state', '$stateParams' , 'FaseProjetoService', 'Feedback','EntregaSprintService', 'SweetAlert',
($scope, $location, $timeout, $http, urlConfig, faseProjetoResolve, $state, $stateParams, FaseProjetoService, Feedback, EntregaSprintService, SweetAlert)->
  
  $scope.faseProjetoResolve = faseProjetoResolve?.data
  $scope.btnDisabled = false

  $scope.novaFaseProjeto = ->
    $state.go('web.projeto.gerenciar.fases.criar')

  $scope.editarFaseProjeto = (faseProjeto) ->
    $state.go('web.projeto.gerenciar.fases.editar', {'id': $stateParams.id,'faseId': faseProjeto.id })

  $scope.excluirFaseProjeto = (faseProjeto)->
    SweetAlert.swal {
      title: 'Você tem certeza?'
      text: 'Não será possível desfazer essa operação!!'
      type: 'warning'
      showCancelButton: true
      closeOnConfirm: true
    },
    (isConfirmed)->
      if isConfirmed
        FaseProjetoService.excluir(faseProjeto.id)
          .success (data)->
            SweetAlert.swal({title:"Item exluído!",text:"Operação realizada com sucesso!",type:"success"})
            $state.reload()
          .error (error)->
            SweetAlert.swal({title:"OPS!",text:error.message,type:"error"})  

  ]

