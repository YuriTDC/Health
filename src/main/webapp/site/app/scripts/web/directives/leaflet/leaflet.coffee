angular.module "leaflet-module", [
]


.directive "lf", [
	'$timeout'
	($timeout)->
		restrict: 'EA'
		transclude: true
		scope:
			geojson: '='
			onSelectFeature: '='
			updatedCollection: '='
		template: '<div></div>'
		link: (scope, element, attrs) ->

			states =
				empty:
					fillOpacity : 0
					opacity     : 0

				filled:
					fillOpacity : 0.5
					opacity     : 1

				over:
					fillOpacity : 0.2
					opacity     : 1


			highlightFeature = (e)->
				layer = e.target
				layer.setStyle states.over

				if !L.Browser.ie && !L.Browser.opera
					layer.bringToFront()

			resetHighlight = (e)->
				layer = e.target
				feature = layer.feature

				mapaModel = _.findWhere scope.geojson, {id: feature.id}

				if mapaModel.properties?.espacoFisico?
					layer.setStyle states.filled
				else
					layer.setStyle states.empty

				return


			getContentPopover = (feature)->
				html = ''
				espacoFisicoJSON = feature.properties.espacoFisico
				if espacoFisicoJSON
					html = """
						<div class="espaco-detalhes" id="feature-#{feature.id}">
							<h1>#{espacoFisicoJSON.nome}</h1>
							<span>Número de quartos</span> #{espacoFisicoJSON.caracteristicas.qtdeQuarto}<br>
							<span>Cama de casal</span> #{espacoFisicoJSON.caracteristicas.qtdeCamaCasal}<br>
							<span>Cama de solteiro</span> #{espacoFisicoJSON.caracteristicas.qtdeCamaSolteiro}<br>
							<span>Beliche</span> #{espacoFisicoJSON.caracteristicas.qtdeBeliche}<br>
							<span>Cama extra</span> #{espacoFisicoJSON.caracteristicas.qtdeCamaExtra}<br>
							<span>Adaptado cadeirante</span> #{if espacoFisicoJSON.caracteristicas.adaptado then 'Sim' else 'Não'}<br>
							<div class="espaco-footer">
								<a id="btn-editar"  data-id="#{feature.id}" class="btn btn-secondary">Selecionar</a>
								<a id="btn-excluir" data-id="#{feature.id}" class="btn">Excluir</a>
							</div>
						</div>
					"""
					html


			getColor = (feature)->
				if feature.properties?.espacoFisico.status is 'DISPONIVEL'
					return '#2BB95A'

				if feature.properties?.espacoFisico.status is 'INDISPONIVEL'
					return '#F4897F'

				if feature.properties?.espacoFisico.status is 'RESERVADO'
					return '#BA9A39'

				'#2BB95A'


			getFillColor = (feature)->
				if feature.properties?.espacoFisico.status is 'DISPONIVEL'
					return '#176732'

				if feature.properties?.espacoFisico.status is 'INDISPONIVEL'
					return '#812423'

				if feature.properties?.espacoFisico.status is 'RESERVADO'
					return '#6B5921'

				'#176732'


			updateLayers = (current, past)->

				featureJson = {
					type: "FeatureCollection",
					features: scope.geojson
				}

				scope.geojsonObj = L.geoJson featureJson,
					style: (feature) =>
						{
						weight      : 1
						color       : getColor(feature)
						fillColor   : getFillColor(feature)
						opacity     : 1

						}
					,
					filter: (feature)->
						if !feature.properties.espacoFisico
							return false


						if scope.updatedCollection
							if feature.properties.espacoFisico?.conjunto
								for acomodacao in scope.updatedCollection
									if _.contains feature.properties.espacoFisico.espacoFilhoIds, acomodacao.id
										return true
								return false

							else
								for acomodacao in scope.updatedCollection
									if feature.properties.espacoFisico.id is acomodacao.id
										return true
								return false
						else
							return true
					,
					onEachFeature: (feature, layer) =>
						###layer.on
							mouseover   : highlightFeature
							mouseout    : resetHighlight###

						###if feature.properties && feature.properties.espacoFisico
							popupContent = getContentPopover feature
							layer.bindPopup popupContent
						else###
						layer.on 'click', =>
							$timeout ->
								scope.selectFeature feature
								do scope.mapa.closePopup
								mapaId = feature.id
								mapaModel = _.findWhere scope.mapa, {id: mapaId}
	#							openModal layer, mapaModel
					,
					pointToLayer: (feature, latlng) =>
						L.circleMarker latlng,
							radius      : 10


				.addTo(scope.mapa)
				return


			render = ->

				scope.mapa = L.map attrs.id,
					center  : [0.238, 0.485]
					minZoom : 10
					zoom    : 11
					maxZoom : 12
					crs     : L.CRS.Simple

				imageUrl = '/images/mapa_bertioga.jpg'
				imageBounds = [[0, 0], [0.476, 0.97]]
				L.imageOverlay(imageUrl, imageBounds).addTo(scope.mapa)

				scope.mapa.setMaxBounds [[-0.02, -0.02], [0.476, 0.97]]


				do updateLayers

			scope.selectFeature = (feature)->
				scope.onSelectFeature feature


			scope.$watch 'geojson', (newVal, oldVal)->
				if newVal
					if !scope.mapa
						do render
					else
						if newVal and oldVal
							console.log _.difference newVal, oldVal
							do scope.geojsonObj.clearLayers if scope.geojsonObj
							updateLayers newVal, oldVal
				return
			, true


]
