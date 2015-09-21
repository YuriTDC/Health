angular.module('emailOverviewWebApp.filters')

.filter("semanaFilter", [
	()->
		(value)->
			switch value
				when 1  then "Segunda"
				when 2  then "Terça"
				when 3  then "Quarta"
				when 4  then "Quinta"
				when 5  then "Sexta"
				when 6  then "Sábado"
				when 7  then "Domingo"

])
