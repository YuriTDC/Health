angular.module('emailOverviewWebApp.filters')

.filter("mesReferencia", [
  ()->
    (data)->
        value = data.split('/')
        switch value[1]
            when '01'  then "Janeiro"
            when '02'  then "Fevereiro"
            when '03'  then "Março"
            when '04'  then "Abril"
            when '05'  then "Maio"
            when '06'  then "Junho"
            when '07'  then "Julho"
            when '08'  then "Agosto"
            when '09'  then "Setembro"
            when '10' then "Outubro"
            when '11' then "Novembro"
            when '12' then "Dezembro"
])

.filter("mesAmericano", [
    () ->
        (mes) ->
            if mes is 'Janeiro' or mes is '01'
              return 'January'
            else if mes is 'Fevereiro' or mes is '02'
              return 'February'
            else if mes is 'Março' or mes is '03'
              return 'March'
            else if mes is 'Abril' or mes is '04'
              return 'April'
            else if mes is 'Maio' or mes is '05'
              return 'May'
            else if mes is 'Junho' or mes is '06'
              return 'June'
            else if mes is 'Julho' or mes is '07'
              return 'July'
            else if mes is 'Agosto' or mes is '08'
              return 'August'
            else if mes is 'Setembro' or mes is '09'
              return 'September'
            else if mes is 'Outubro' or mes is '10'
              return 'October'
            else if mes is 'Novembro' or mes is '11'
              return 'November'
            else
              return 'December'
            return false

])
