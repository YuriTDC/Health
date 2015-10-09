angular.module('health.services', [])

/**
 * A simple example service that returns some data.
 */
.factory('Socorros', function() {
  // Might use a resource here that returns a JSON array

  // Some fake testing data
  var socorros = [
    { id: 0, nome: 'Afogamento',    pagina: 'templates/socorros-afogamento.html' },
    { id: 1, nome: 'Fratura e torção', pagina: 'templates/socorros-fratura.html' },
    { id: 2, nome: 'Intoxicação',   pagina: 'templates/socorros-intoxicacao.html' },
    { id: 3, nome: 'Queimaduras',   pagina: 'templates/socorros-queimaduras.html' } 
  ];

  return {
    all: function() {
      return socorros.map(function (socorro) {
        return {
            id: socorro.id,
            nome: socorro.nome
        };
      });
    },
    get: function(socorroId) {
      // Simple index lookup
      return socorros.filter(function (socorro) {
        return socorro.id === +socorroId;
      })[0];
    }
  }
})

/**
 * A simple example service that returns some data.
 */
.factory('Medicos', function() {
var medicos = [{id:1,nome:'Breno de Ciqueira',crm:'121689',especialidades:['Cardiologia'],telefone:'3711-6666',locais:[{descricao:'Consultorio',endereco:'Rua General Carneiro, 1595 Centro - Franca',latlang:[-20.5363421,-47.3975881],telefone:'1137-0000'},{descricao:'Hospital',endereco:'Rua General Carneiro, 280 Centro - Franca',latlang:[-20.5336468,-47.4100698],telefone:'1440-5106'}]},
{id:2,nome:'Carlos Alves Pereira',crm:'33382',especialidades:['Pediatria', 'Ortopedia'],telefone:'3711-6969',locais:[{descricao:'Hospital',endereco:'Rua General Carneiro, 280',latlang:[-20.5408767,-47.4119313],telefone:'1137-1234'},{descricao:'Clinica Pediatrica',endereco:'Rua João dos Santos Ferreira, 595-807 - Jardim Paulistano',latlang:[-20.524314, -47.360269],telefone:'(16) 3705-3056'}]},
{id:3,nome:'Pedro Alvares',crm:'5614548',especialidades:['Anatomia Patalogica'],telefone:'(16) 3711-6666',locais:[{descricao:'Consultorio',endereco:'RUA JOAQUIM SPERETA 410',latlang:[-20.500263, -47.414049],telefone:'1147-1265'},{descricao:'Consultorio',endereco:'Rua André Fernandes, 70 - Boa Vista, SP',latlang:[-20.528665, -47.405417],telefone:'9173-1346'}]},
{id:4,nome:'Roberto dos Santos',crm:'65898',especialidades:['Acunpuntura'],telefone:'(16) 3725-6846',locais:[{descricao:'Consultorio',endereco:'RUA ABILIO COUTINHO 420',latlang:[-20.5392596,-47.425464],telefone:'1157-1295'}]},
{id:5,nome:'João Cleber',crm:'6985',especialidades:['Dermatologia'],telefone:'(16) 3824-7896',locais:[{descricao:'Hospital',endereco:'Rua Miguel Fernando Pianura, 360-472 - Vila Totoli',latlang:[-20.513758, -47.392306],telefone:'1457-2568'}]},
{id:6,nome:'Miguel da Silva',crm:'5469',especialidades:['Clinica Geral', 'Pé e Joelho'],telefone:'5869-5242',locais:[{descricao:'Consultorio',endereco:'Avenida Paschoal Pulicano, 1169 - Jardim Francano, SP',latlang:[-20.546679, -47.412475],telefone:'5863-4152'}]},
{id:7,nome:'Jose Silva Sauro',crm:'8855',especialidades:['Infectologia'],telefone:'8524-9636',locais:[{descricao:'Clinica',endereco:'Rua Artur Sabóia, 461-469 - Paraíso, SP',latlang:[-23.578738, -46.637650],telefone:'7894-4236'}]},
{id:8,nome:'Wesley Miguel',crm:'78962',especialidades:['Clinica Geral'],telefone:'8529-7896',locais:[{descricao:'Hospital',endereco:'Avenida das Seringueiras, 1-  Franca - SP',latlang:[-20.552616, -47.421939],telefone:'1234-8526'}]},
{id:9,nome:'Diogenes Pedro',crm:'96333',especialidades:['Consultorio'],telefone:'7391-8246',locais:[{descricao:'Clinica de Fisio',endereco:'Rua José Ribeiro Conrado, 290 - São José, SP',latlang:[-20.533181, -47.388944],telefone:'4712-5803'}]},
{id:10,nome:'Samara Hue',crm:'455118',especialidades:['Endocrinologia'],telefone:'1346-5852',locais:[{descricao:'Hospital do Cancer',endereco:'Avenida Presidente Vargas, 2247  SP',latlang:[-20.518557, -47.382679],telefone:'7845-2356'}]},
{id:11,nome:'Eduardo Neaime',crm:'456789',especialidades:['Ginecologista'],telefone:'1346-5852',locais:[{descricao:'Starbucks',endereco:'Avenida Presidente Vargas, 2247  SP',latlang:[-20.518557, -47.382679],telefone:'7845-2356'}]}
]


  return {
    all: function() {
      return medicos.map(function (medico) {
        return {
            id: medico.id,
            nome: medico.nome
        };
      });
    },
    especialidades: function (  ) {
        var retorno = medicos.reduce(
            function (anterior, atual, indice, colecao) {
                return anterior.concat( atual.especialidades );
            }, []
        ).reduce(
            function (anterior, atual, indice, colecao) {
                if (anterior.indexOf(atual) < 0) anterior.push(atual);
                return anterior;
            }, []
        ).sort();
        
        return retorno;
    },
    medicosPorEspecialidade: function ( criterio ) {
    
        criterio = criterio.toLocaleLowerCase();
    
        var retorno = medicos.filter(function ( medico ) {
        
            var temp = medico.especialidades.join('-').toLocaleLowerCase();
            
            return temp.indexOf(criterio) > -1;
        }).map(function (medico) {
            return {
                id: medico.id,
                nome: medico.nome,
                crm: medico.crm,
                especialidades: medico.especialidades.join(', ')
            }
        }).sort(function (A, B) {
            return A.nome.toLocaleLowerCase().localeCompare(B.nome.toLocaleLowerCase());
        });
        
        return retorno;
    },
    get: function(medicoId) {
      // Simple index lookup
      return medicos.filter(function (medico) {
        return medico.id === +medicoId;
      })[0];
    }
  }
})
//-----------------------------------------------------------
.factory('Prestadores', function() {
var prestadores = [{id:1,descricao:'Lab. de Patologia Dra. Maria Heloisa Rached',endereco:'RUA DOUTOR FERNANDO FALEIROS LIMA 2233',latlang:[-20.5392074,-47.3908864],telefone:'(16) 3722-2463',especialidades:['Patologia']},
{id:2,descricao:'Delboni Auriemo',endereco:'Avenida Juruá, 477 - Alphaville Industrial, SP',latlang:[-23.500193, -46.855859],telefone:'7869-1232',especialidades:['Exames Laboratorias']},
{id:3,descricao:'Consultorio',endereco:'Rua General Carneiro, 1595 Centro - Franca',latlang:[-20.5363421,-47.39758817],telefone:'1137-0000',especialidades:['Cardiologia']},
{id:4,descricao:'Hospital',endereco:'Rua General Carneiro, 280',latlang:[-20.5408767,-47.4119313],telefone:'1137-1234',especialidades:['Pediatria', 'Ortopedia']},
{id:5,descricao:'Consultorio',endereco:'RUA JOAQUIM SPERETA 410',latlang:[-20.500263, -47.414049],telefone:'1147-1265',especialidades:['Anatomia Patalogica']},
{id:6,descricao:'Consultorio',endereco:'RUA ABILIO COUTINHO 420',latlang:[-20.5392596,-47.425464],telefone:'1157-1295',especialidades:['Acunpuntura']},
{id:7,descricao:'Hospital',endereco:'Rua Miguel Fernando Pianura, 360-472 - Vila Totoli',latlang:[-20.513758, -47.392306],telefone:'1457-2568',especialidades:['Dermatologia']},
{id:8,descricao:'Consultorio',endereco:'Avenida Paschoal Pulicano, 1169 - Jardim Francano, SP',latlang:[-20.546679, -47.412475],telefone:'5863-4152',especialidades:['Pé e Joelho', 'Clinica Geral']},
{id:9,descricao:'Clinica',endereco:'Rua Artur Sabóia, 461-469 - Paraíso, SP',latlang:[-23.578738, -46.637650],telefone:'7894-4236',especialidades:['Infectologia']},
{id:10,descricao:'Hospital',endereco:'Avenida das Seringueiras, 1-  Franca - SP',latlang:[-20.552616, -47.421939],telefone:'1234-8526',especialidades:['Clinica Geral']},
{id:11,descricao:'Clinica de Fisio',endereco:'Rua José Ribeiro Conrado, 290 - São José, SP',latlang:[-20.533181, -47.388944],telefone:'4712-5803',especialidades:['Consultorio']},
{id:12,descricao:'Hospital do Cancer',endereco:'Avenida Presidente Vargas, 2247  SP',latlang:[-20.518557, -47.382679],telefone:'7845-2356',especialidades:['Endocrinologia']},
{id:13,descricao:'Hospital',endereco:'Rua General Carneiro, 280 Centro - Franca',latlang:[-20.5336468,-47.4100698],telefone:'1440-5106',especialidades:['Cardiologia']},
{id:14,descricao:'Clinica Pediatrica',endereco:'Rua João dos Santos Ferreira, 595-807 - Jardim Paulistano',latlang:[-20.524314, -47.360269],telefone:'(16) 3705-3056',especialidades:['Pediatria', 'Ortopedia']},
{id:15,descricao:'Consultorio',endereco:'Rua André Fernandes, 70 - Boa Vista, SP,',latlang:[-20.528665, -47.405417],telefone:'9173-1346',especialidades:['Anatomia Patalogica']}];


  return {
    all: function() {
      return prestadores.map(function (prestador) {
        return {
            id: prestador.id,
            descricao: prestador.descricao
        };
      });
    },
    especialidades: function (  ) {
        var retorno = prestadores.reduce(
            function (anterior, atual, indice, colecao) {
                return anterior.concat( atual.especialidades );
            }, []
        ).reduce(
            function (anterior, atual, indice, colecao) {
                if (anterior.indexOf(atual) < 0) anterior.push(atual);
                return anterior;
            }, []
        ).sort();
        
        return retorno;
    },
    prestadoresPorEspecialidade: function ( criterio ) {
    
        criterio = criterio.toLocaleLowerCase();
    
        var retorno = prestadores.filter(function ( prestador ) {
        
            var temp = prestador.especialidades.join('-').toLocaleLowerCase();
            
            return temp.indexOf(criterio) > -1;
        }).map(function (prestador) {
            return {
                id: prestador.id,
                descricao: prestador.descricao,
                telefone: prestador.telefone,
                endereco: prestador.endereco,
                especialidades: prestador.especialidades.join(', ')
            }
        }).sort(function (A, B) {
            return A.descricao.toLocaleLowerCase().localeCompare(B.descricao.toLocaleLowerCase());
        });
        
        return retorno;
    },
    get: function(prestadorId) {
      // Simple index lookup
      return prestadores.filter(function (prestador) {
        return prestador.id === +prestadorId;
      })[0];
    }
  }
})

//-----------------------------------------------------------
.factory('Farmacias', function() {
var farmacias = [{id:1,descricao:'Ultrafarma',endereco:'Avenida Santa Cruz, 2535-2559',latlang:[-20.542201, -47.385501],telefone:'(16) 3018-1167',especialidades:['24 Horas'],distancia:'600 metros'},
{id:2,descricao:'Droga Raia',endereco:'Avenida Paulo VI, 998 - Jardim Alvorada, SP',latlang:[-20.559822, -47.402223],telefone:'(16) 3702-5338',especialidades:['Horário comercial'],distancia:'300 metros'}];


  return {
    all: function() {
      return farmacias.map(function (farmacia) {
        return {
            id: farmacia.id,
            descricao: farmacia.descricao
        };
      });
    },
    especialidades: function (  ) {
        var retorno = farmacias.reduce(
            function (anterior, atual, indice, colecao) {
                return anterior.concat( atual.especialidades );
            }, []
        ).reduce(
            function (anterior, atual, indice, colecao) {
                if (anterior.indexOf(atual) < 0) anterior.push(atual);
                return anterior;
            }, []
        ).sort();
        
        return retorno;
    },
    farmaciasPorEspecialidade: function ( criterio ) {
    
        criterio = criterio.toLocaleLowerCase();
    
        var retorno = farmacias.filter(function ( farmacia ) {
        
            var temp = farmacia.especialidades.join('-').toLocaleLowerCase();
            
            return temp.indexOf(criterio) > -1;
        }).map(function (farmacia) {
            return {
                id: farmacia.id,
                descricao: farmacia.descricao,
                telefone: farmacia.telefone,
                distancia: farmacia.distancia,
                endereco: farmacia.endereco,
                especialidades: farmacia.especialidades.join(', ')
            }
        }).sort(function (A, B) {
            return A.descricao.toLocaleLowerCase().localeCompare(B.descricao.toLocaleLowerCase());
        });
        
        return retorno;
    },
    get: function(farmaciaId) {
      // Simple index lookup
      return farmacias.filter(function (farmacia) {
        return farmacia.id === +farmaciaId;
      })[0];
    }
  }
});



