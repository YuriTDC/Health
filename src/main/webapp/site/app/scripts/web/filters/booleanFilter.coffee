angular.module('emailOverviewWebApp.filters')
  .filter("booleanFilter", [
    ()->
      (bol) ->
        if bol is true
          return 'Sim'
        else
          return 'Não'
  ])




