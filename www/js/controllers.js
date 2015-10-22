angular.module('health.controllers', [])

.controller('MapController', function($scope, $cordovaGeolocation, $ionicLoading) {
     
    document.addEventListener("deviceready", onDeviceReady, false);
     
    function onDeviceReady() {
         
        $ionicLoading.show({
            template: '<ion-spinner icon="bubbles"></ion-spinner><br/>Acquiring location!'
        });
         
        var posOptions = {
            enableHighAccuracy: true,
            timeout: 20000,
            maximumAge: 0
        };
        $cordovaGeolocation.getCurrentPosition(posOptions).then(function (position) {
            var lat  = position.coords.latitude;
            var long = position.coords.longitude;
             
            var myLatlng = new google.maps.LatLng(lat, long);
             
            var mapOptions = {
                center: myLatlng,
                zoom: 16,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };          
             
            var map = new google.maps.Map(document.getElementById("map"), mapOptions);          
             
            $scope.map = map;   
            $ionicLoading.hide();           
             
        }, function(err) {
            $ionicLoading.hide();
            console.log(err);
        });
    }               
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




