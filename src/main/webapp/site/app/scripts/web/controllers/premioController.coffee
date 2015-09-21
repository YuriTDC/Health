angular.module('emailOverviewWebApp.controllers')
.controller 'PremioController', ['$scope', '$location', '$timeout', '$http', 'urlConfig', 'premio', '$state', 'PremioService', 'Feedback', 'SweetAlert',
($scope, $location, $timeout, $http, urlConfig, premio, $state, PremioService, Feedback, SweetAlert)->
  
  $scope.premio = premio
  $scope.btnDisabled = false

  $scope.novoPremio = ->
    $state.go('web.premio.criar')

  $scope.editarPremio = (premio) ->
    $state.go('web.premio.editar', {'id': premio.id })

  $scope.listPremio = ->
    $state.go('web.premio.listar')  

  $scope.excluirPremio = (premio)->
    SweetAlert.swal {
      title: 'Você tem certeza?'
      text: 'Não será possível desfazer essa operação!!'
      type: 'warning'
      showCancelButton: true
      closeOnConfirm: true
    },
    (isConfirmed)->
      if isConfirmed
        PremioService.excluir(premio.id)
          .success (data)->
            SweetAlert.swal({title:"Item exluído!",text:"Operação realizada com sucesso!",type:"success"})
            $state.reload()
          .error (error)->
            SweetAlert.swal({title:"OPS!",text:error.message,type:"error"}) 

]

