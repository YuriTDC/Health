angular.module('emailOverviewWebApp.services')
.service('PremioService', [
  '$rootScope', '$http', 'httpUtils','urlConfig'
  ($rootScope, $http, httpUtils, urlConfig) ->

      user = {}

      findAll: ->
        $http
          url: "#{urlConfig.baseUrl}/premio",
          method: "GET"

      recuperar: (id)->
        $http
          url: "#{urlConfig.baseUrl}/premio/#{id}",
          method: "GET"

      editar: (id, premio)->
        $http
          url: "#{urlConfig.baseUrl}/premio/#{id}",
          method: "PUT"
          data:
            premio
          headers:
            "Content-Type": "application/json"

      recuperar: (id)->
        $http
          url: "#{urlConfig.baseUrl}/premio/#{id}",
          method: "GET"

      excluir: (id)->
        $http
          url: "#{urlConfig.baseUrl}/premio/#{id}",
          method: "DELETE"

      create: (premio)->
        $http
          url: "#{urlConfig.baseUrl}/premio",
          method: "POST"
          data:
            premio
          headers:
            "Content-Type": "application/json"

  ])