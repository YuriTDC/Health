angular.module('emailOverviewWebApp.filters')
  .filter("formatDataAndHora", [
    ()->
      (input) ->
        data = []
        data = input.split(' ')
        return data[0] + ", " + data[1]
  ])




