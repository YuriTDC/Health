angular.module('health.controllers', [])

.controller('MapCtrl', function($scope, $state, $cordovaGeolocation) {
  var options = {timeout: 10000, enableHighAccuracy: true};

  $cordovaGeolocation.getCurrentPosition(options).then(function(position){
 
    var latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
 
    var mapOptions = {
      center: latLng,
      zoom: 15,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
 
    $scope.map = new google.maps.Map(document.getElementById("map"), mapOptions);

    //Aguarda ate o map ser renderizado
    google.maps.event.addListenerOnce($scope.map, 'idle', function(){
     
      var marker = new google.maps.Marker({
          map: $scope.map,
          animation: google.maps.Animation.DROP,
          position: latLng
      });      
     
      var infoWindow = new google.maps.InfoWindow({
          content: "Teste pop-up"
      });
     
      google.maps.event.addListener(marker, 'click', function () {
          infoWindow.open($scope.map, marker);
      });
     
    });
 
  }, function(error){
    console.log("Não foi possivel localizar sua posição... :(");
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




