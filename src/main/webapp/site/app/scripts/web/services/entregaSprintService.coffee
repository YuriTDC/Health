angular.module('emailOverviewWebApp.services')
.service('EntregaSprintService', [
  '$rootScope', '$http', 'httpUtils','urlConfig'
  ($rootScope, $http, httpUtils, urlConfig) ->

      user = {}

      findAll: ->
        $http
          url: "#{urlConfig.baseUrl}/entregaSprint",
          method: "GET"

      findByFase: (faseId) ->
        $http
          url: "#{urlConfig.baseUrl}/entregaSprint/fase/#{faseId}",
          method: "GET"

      recuperar: (id)->
        $http
          url: "#{urlConfig.baseUrl}/entregaSprint/#{id}",
          method: "GET"

      excluir: (id)->
        $http
          url: "#{urlConfig.baseUrl}/entregaSprint/#{id}",
          method: "DELETE"

      editar: (id, entregaSprint)->
        $http
          url: "#{urlConfig.baseUrl}/entregaSprint/#{id}",
          method: "PUT"
          data:
            entregue: entregaSprint.entregue
            faseId: entregaSprint.faseId
            integrantes: _.pluck(entregaSprint.integrantes, 'id')
            dataEntrega: entregaSprint.dataEntrega
          headers:
            "Content-Type": "application/json"

      create: (entregaSprint)->
        $http
          url: "#{urlConfig.baseUrl}/entregaSprint",
          method: "POST"
          data:
            entregue: entregaSprint.entregue
            faseId: entregaSprint.faseId
            integrantes: _.pluck(entregaSprint.integrantes, 'id')
            dataEntrega: entregaSprint.dataEntrega
          headers:
            "Content-Type": "application/json"
           

  ])