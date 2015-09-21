angular.module('emailOverviewWebApp.services')
.service('IntegranteService', [
  '$rootScope', '$http', 'httpUtils','urlConfig'
  ($rootScope, $http, httpUtils, urlConfig) ->

      user = {}

      findAll: ->
        $http
          url: "#{urlConfig.baseUrl}/integrante",
          method: "GET"

      findOthers: (id)->
        $http
          url: "#{urlConfig.baseUrl}/integrante/others/#{id}",
          method: "GET"

      recuperar: (id)->
        $http
          url: "#{urlConfig.baseUrl}/integrante/#{id}",
          method: "GET"

      excluir: (id)->
        $http
          url: "#{urlConfig.baseUrl}/integrante/#{id}",
          method: "DELETE"

      editar: (id, integrante)->
        $http
          url: "#{urlConfig.baseUrl}/integrante/#{id}",
          method: "PUT"
          data:
            integrante
          headers:
            "Content-Type": "application/json"

      create: (integrante)->
        $http
          url: "#{urlConfig.baseUrl}/integrante",
          method: "POST"
          data:
            integrante
          headers:
            "Content-Type": "application/json"

      listTokens: ->
        $http
          url: "#{urlConfig.baseUrl}/integrante/tokens",  
          method: "GET"

      recuperarTokensIntegrante: (id)->
        $http
          url: "#{urlConfig.baseUrl}/integrante/tokens/#{id}",
          method: "GET"

      tokensRestantes: (id)->
        $http
          url: "#{urlConfig.baseUrl}/integrante/tokens/restantes/#{id}",
          method: "GET"
  ])
