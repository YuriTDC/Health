ngular.module('emailOverviewWebApp.services')
.service('StatusService', [
  '$rootScope', '$http', 'httpUtils','urlConfig'
  ($rootScope, $http, httpUtils, urlConfig) ->

      user = {}

      findAll: (params)->
        $http
          url: "#{urlConfig.baseUrl}/status",
          method: "GET"
  ])
