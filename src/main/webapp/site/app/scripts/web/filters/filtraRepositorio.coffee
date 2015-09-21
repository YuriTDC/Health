angular.module('emailOverviewWebApp.filters')

.filter("selectFilter", [
  ()->
    myFilter =  (arr, param)->
      newArray = []
      if param
        if param? && param.notificar == true
          _.each arr, (obj) ->
            newArray.push obj if obj.notificar is true

        else if param? && param.notificar == false
          _.each arr, (obj) ->
            newArray.push obj if obj.notificar is false or obj.notificar is null

        return newArray
      else
        return arr

    return myFilter

])




