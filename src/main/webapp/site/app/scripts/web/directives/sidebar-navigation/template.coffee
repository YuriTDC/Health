angular.module('template-sbarNavigation',[])
.run(($templateCache)->
  $templateCache.put("sidebar-navigation/template/pai.html",
                    """
      <ul id="sidebarNav">
        <li ng-repeat="item in items">
          <div ng-class="!collapsed && item.periodos.length > 0 ? 'titulo-menu-open' : 'titulo-menu'"  ng-click="collapsed = !collapsed"><a ng-bind="item.titulo.nome" class="urlDiretiva" ng-href="{{item.titulo.url}}"></a></div>
          <ul class="periodo-list" ng-show="item.periodos.length > 0 && !collapsed" id="periodos">

            <p class="titulo-periodo" ng-show="item.acomodacoes.length > 0 && !collapsed">TIPOS DE ACOMODAÇÕES </p>
            <li ng-repeat="acomodacao in item.acomodacoes track by $index" style="height:115px;" ng-init="acomodacao.ordem = $index">

              <div class="sort-position">
                <span ng-show="!$first"><i class="fa fa-sort-asc fa-4"  ng-click="subir($index,'acomodacao')"></i></span>
                <span class="index-position" ng-bind="$index + 1"></span>
                <span ng-show="!$last"><i class="fa fa-sort-desc fa-4" ng-click="descer($index, 'acomodacao')"> </i></span>
              </div>

              <div class="periodo-info">
                <span ng-bind="acomodacao.qtdAcomodacoes"></span> acomodações com capacidade para
                <span ng-bind="acomodacao.min"></span> a
                <span ng-bind="acomodacao.max"></span> pessoas
                <div></div>
                <button class="btn btn-default pull-right btn-search-periodo"><i class="fa fa-search"></i></button>
                <button ng-click="remover($index, 'acomodacao')" class="btn btn-default pull-right btn-trash-periodo"><i class="fa fa-trash"></i></button>
              </div>

            </li>

          <div style="text-align:center;border-bottom: solid 1px #fff;" ng-show="item.hospedes > 0">
            <p style="color:#FFF;text-align:center;">HÓSPEDES</p>
            <p style="color:#FFF;display:inline;"  ng-bind="item.hospedes"></p>
            <p style="color:#FFF;display:inline;" >pessoas</p>
            <div style="text-align: right;margin: 0 40px 20px 0;">
              <button class="btn btn-default" ui-sref="web.hospedagemWizard.hospedes"><i class="fa fa-pencil"></i></button>
              <button class="btn btn-default"><i class="fa fa-search"></i></button>
            </div>
          </div>

          <p class="titulo-periodo" ng-show="item.periodos.length > 0 && !collapsed">PERÍODOS ESCOLHIDOS <i ng-show="btn" class="fa fa-plus-square" ng-click="redirecionar(caminho)"></i> </p>
            <li ng-repeat="periodo in item.periodos track by $index">

              <div class="sort-position">
                <span ng-show="!$first"><i class="fa fa-sort-asc fa-4"  ng-click="subir($index, 'periodo')"></i></span>
                <span class="index-position" ng-bind="$index + 1"></span>
                <span ng-show="!$last"><i class="fa fa-sort-desc fa-4" ng-click="descer($index, 'periodo')"> </i></span>
              </div>

              <div class="periodo-info">
                <span ng-bind="periodo.inicioVigencia | somenteData"></span> a
                <span ng-bind="periodo.fimVigencia | somenteData"></span>
                <div></div>
                <span ng-bind="periodo.diaria.quantidade"></span> <span>diárias</span>
                <button class="btn btn-default pull-right btn-search-periodo"><i class="fa fa-search"></i></button>
                <button ng-click="remover($index)" class="btn btn-default pull-right btn-trash-periodo"><i class="fa fa-trash"></i></button>
              </div>

            </li>
          </ul>
        </li>
      </ul>
      """
  )
)
