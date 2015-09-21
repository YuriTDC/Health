angular.module('emailOverviewWebApp.filters')

.filter("somenteData", [
	()->
		(value)->
			if value
				return moment(value, 'DD/MM/YYYY').format('DD/MM/YYYY')
			return value
])
