angular.module('emailOverviewWebApp.filters')

.filter("somenteHorarioFilter", [
	()->
		(value)->
			moment(value, 'DD/MM/YYYY HH:mm').format('HH:mm')
])
