angular.module('emailOverviewWebApp.services')
.service('TrocaPremioService', [
  '$rootScope', '$http', 'httpUtils','urlConfig'
  ($rootScope, $http, httpUtils, urlConfig) ->

      user = {}

      findAll: ->
        $http
          url: "#{urlConfig.baseUrl}/trocaPremio",
          method: "GET"

      recuperar: (id)->
        $http
          url: "#{urlConfig.baseUrl}/trocaPremio/#{id}",
          method: "GET"

      rankPremio: ->
        $http
          url: "#{urlConfig.baseUrl}/trocaPremio/rankPremio",
          method: "GET" 

      trocaSemPremio: ->
        $http
          url: "#{urlConfig.baseUrl}/trocaPremio/trocaSemPremio",
          method: "GET" 
          
      excluir: (id)->
        $http
          url: "#{urlConfig.baseUrl}/trocaPremio/#{id}",
          method: "DELETE"

      create: (trocaPremio)->
        $http
          url: "#{urlConfig.baseUrl}/trocaPremio",
          method: "POST"
          data:
            integranteId: trocaPremio.integrante.id
            premioId: trocaPremio.premio.id
          headers:
            "Content-Type": "application/json"

  ])