angular.module('emailOverviewWebApp.controllers')
.controller 'EditarIntegranteController',['$scope', '$location', '$timeout', '$http', 'urlConfig', 'integrante', '$state', 'IntegranteService', 'Feedback', 'UploadService'
($scope, $location, $timeout, $http, urlConfig, integrante, $state, IntegranteService, Feedback, UploadService)->

  $scope.integrante = integrante
  $scope.btnDisabled = false
  $scope.tipoForm = 'EDITAR INTEGRANTE'

  fileName = integrante.avatar.split('/')
  $scope.filename = _.last(fileName)

  $scope.disableSalvar = ->
    if $scope.integrante.avatar is null or $scope.integrante.avatar is ''
      return true
    else
      return false

  $scope.save = ->
    $scope.btnDisabled = true
    IntegranteService.editar($scope.integrante.id, _.omit($scope.integrante, 'id')).then (result) ->
      $scope.btnDisabled = false
      Feedback.success 'Integrante alterado com sucesso.'
      $state.go 'web.integrante.listar'
    (error) ->
      Feedback.fail 'Falha ao salvar integrante.'
      $scope.btnDisabled = false

  $scope.upload = (inputFile) ->
    file = inputFile[0].files[0]
    $scope.btnDisabled = true
    $scope.disableUploadButton = true
    #     method do clear input file
    clearInput = ->
      input = $(inputFile[0])
      input.replaceWith ( input = input.clone(true) ) #TODO: find another way to clear input file

    uploadCallback = (result)->
      $scope.integrante.avatar = result.path
      $scope.filename = result.file_name
      $scope.disableUploadButton = false
      $scope.btnDisabled = false
      #$scope.fileUrl = "#{urlConfig.baseUrl}arquivo/#{result.fileName}"
      return

    errorCallback = ->
      $scope.disableUploadButton = false
      $scope.btnDisabled = false
      do clearInput

    progress = (evt)->
      $timeout ->
        if evt.lengthComputable
          $scope.progress = Math.round(evt.loaded * 100 / evt.total)
          $scope.progressActive = if $scope.progress < 100 or $scope.progress is 0 then true else false
          $scope.progress
        return

    if file
      fd = new FormData()
      fd.append "file", file
      UploadService.send(fd, progress).then uploadCallback, errorCallback
    return

]
