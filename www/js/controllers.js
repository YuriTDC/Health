angular.module('health.controllers', [])

.controller('MapCtrl', function($scope, $timeout, $cordovaGeolocation, uiGmapGoogleMapApi, uiGmapIsReady, ngGPlacesAPI) {
  
  var posOptions = {timeout: 10000, enableHighAccuracy: false};
    
    // pega sua posicao atual - latLong
    $cordovaGeolocation
      .getCurrentPosition(posOptions)
      .then(function (position) {
        $scope.lat  = position.coords.latitude;
        $scope.long = position.coords.longitude;

        // pega os locais perto de vc
        ngGPlacesAPI.nearbySearch({
            types: ['pharmacy'],
            latitude: $scope.lat,
            longitude: $scope.long,
        }).then(
            function(data){
              setPlaces(data);
            });

        /*ngGPlacesAPI.placeDetails({
            reference:"CnRkAAAAZvRRKss3vAZtQPl8B-88rqa0fSiNbmlUWzprk4IKVmc-AK3twL3Olw9mcR30JHdZGkX9gyGqJpTWVWQkga3kWlsJOy4_Pg4WxDWfLVzDO80Pl6NpBRgEd_43CYp_1EKpHyAVR8tQPIkJcbJGiKF2XRIQocOdx7FrLOGuBhKzoP5PRxoUnLr4D20Hj5K88mtlidce-V6fSNE"
        }).then(
            function (details) {
              $scope.details = details;
              return details;
        });*/ 

        // renderiza novo mapa baseado na sua geoLocation
        uiGmapGoogleMapApi.then(function(maps){

          $scope.control = {};

          $scope.myMap = {
            center: {
              latitude: $scope.lat,
              longitude: $scope.long
            }, 
            zoom : 16,
          };
          $scope.myMarker = {
            id: 1, 
            coords: {
              latitude: $scope.lat,
              longitude: $scope.long
            }, 
            options: {draggable:false}
          };

        });

      }, function(err) {
        // error
      });

    var setPlaces = function(places){
      $scope.places = places;
      $scope.getDetails();
    };

    var fetchDetail = function(place){

      ngGPlacesAPI.placeDetails({
        reference: place.reference
      }).then(
        function (details) {
          place.details = details;
      });
    };

    $scope.getDetails = function(){
      _($scope.places).forEach(function(place){
        $timeout(function(){
          fetchDetail(place);
        });
      });
    };

    $scope.getMap = function() {
        var map1 = $scope.control.getGMap();
        console.log('map:', map1);
        console.log('locais:', $scope.places);
    };

    uiGmapIsReady.promise(1).then(function(instances) {
        instances.forEach(function(inst) {
        var map = inst.map;
        var uuid = map.uiGmap_id;
        var mapInstanceNumber = inst.instance; 
        });
    });
})

.controller('LoginCtrl', function($scope) {
})

.controller('CadastroCtrl', function($scope, $state) {
    $scope.entrar = function () {
        $state.go('tab.pesquisa');
    };
})

.controller('PesquisaCtrl', function($scope) {
})

.controller('PesqMedicoEspCtrl', function($scope, Medicos) {

    $scope.filtro = '';
    
    $scope.especialidades = Medicos.especialidades();

})

.controller('PesqMedicoEspDetCtrl', function($scope, $stateParams, Medicos) {

    $scope.filtro = '';
    
    $scope.especialidade = $stateParams.esp || 'Médicos';
    
    $scope.medicos = Medicos.medicosPorEspecialidade($stateParams.esp || '');

})

.controller('PesqMedicoContatoCtrl', function($scope, $stateParams, Medicos) {

    $scope.filtro = '';
    
    $scope.medico = Medicos.get($stateParams.medId || 1);
})

.controller('PesqMedicoContatoMapaCtrl', function($scope, $stateParams, $timeout, Medicos) {

    $scope.filtro = '';
    
    $scope.medico = Medicos.get($stateParams.medId || 1);
    $scope.local = $scope.medico.locais[ $stateParams.mapaId || 0 ];
    

})
//-----------------------------------------------------------
.controller('PesqPrestadorEspCtrl', function($scope, Prestadores) {

    $scope.filtro = '';
    
    $scope.especialidades = Prestadores.especialidades();

})

.controller('PesqPrestadorEspDetCtrl', function($scope, $stateParams, Prestadores) {

    $scope.filtro = '';
    
    $scope.especialidade = $stateParams.esp || 'Prestadores';
    
    $scope.prestadores = Prestadores.prestadoresPorEspecialidade($stateParams.esp || '');

})

.controller('PesqPrestadorContatoCtrl', function($scope, $timeout, $stateParams, Prestadores) {

    $scope.filtro = '';
    
    $scope.prestador = Prestadores.get($stateParams.prestadorId || 1);

    $scope.$on('$viewContentLoaded', function() {
        $timeout(function () {
            carregaMapa( '.mapa', $scope.prestador.latlang );
        }, 500);
    });
})

//-----------------------------------------------------------
.controller('PesqFarmaciaCtrl', function($scope, Farmacias) {
    $scope.filtro = '';
    $scope.farmacias = Farmacias.farmaciasPorEspecialidade('');
})

.controller('PesqFarmaciaContatoCtrl', function($scope, $timeout, $stateParams, Farmacias) {

    $scope.filtro = '';
    
    $scope.farmacia = Farmacias.get($stateParams.farmaciaId || 1);
    
    console.log($scope.farmacia);

    $scope.$on('$viewContentLoaded', function() {
        $timeout(function () {
            carregaMapa( '.mapa', $scope.farmacia.latlang );
        }, 500);
    });
})


.controller('ContaCtrl', function($scope) {
})

.controller('SocorrosCtrl', function($scope, Socorros) {
  $scope.socorros = Socorros.all();
})

.controller('SocorrosDetalhesCtrl', function($scope, $stateParams, Socorros) {
  $scope.socorro = Socorros.get($stateParams.socorrosId);
})

.controller('EmergenciaCtrl', function($scope) {
})

.controller('FavoritosCtrl', function($scope) {
}) 





;




