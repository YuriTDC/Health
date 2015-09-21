package io.redspark.email.overview.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.email.overview.comparator.IntegranteSparkComparator;
import io.redspark.email.overview.dto.IntegranteSparksDTO;
import io.redspark.email.overview.entity.EntregaSprint;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Repositorio;
import io.redspark.email.overview.enums.StatusFase;
import io.redspark.email.overview.exceptions.EmailOverviewException;
import io.redspark.email.overview.form.IntegranteForm;
import io.redspark.email.overview.repository.EntregaSprintRepository;
import io.redspark.email.overview.repository.FaseProjetoRepository;
import io.redspark.email.overview.repository.IntegranteRepository;
import io.redspark.email.overview.repository.RepositorioRepository;
import io.redspark.email.overview.repository.TrocaPremioRepository;
import io.redspark.email.overview.service.IntegranteService;

@RestController
@RequestMapping(value = "/integrante")
public class IntegranteController {

	@Autowired
	private IntegranteRepository repository;

	@Autowired
	private RepositorioRepository repositorioRepository;

	@Autowired
	private FaseProjetoRepository faseProjetoRepository;

	@Autowired
	private EntregaSprintRepository entregaSprintRepository;

	@Autowired
	private TrocaPremioRepository trocaPremioRepository;

	@Autowired
	private IntegranteService integranteService;
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public Integrante create(@RequestBody IntegranteForm form) {

		Integrante i = new Integrante();

		i.setNome(form.getNome());
		i.setEmail(form.getEmail());
		i.setAvatar(form.getAvatar());

		i = repository.save(i);

		return i;
	}

	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Integrante edit(@PathVariable("id") Long id,@RequestBody IntegranteForm form) {

		Integrante i = repository.findOne(id);

		i.setNome(form.getNome());
		i.setEmail(form.getEmail());
		i.setAvatar(form.getAvatar());

		i = repository.save(i);

		return i;
	}

	@Transactional
	@RequestMapping(method = RequestMethod.GET)
	public List<Integrante> listar() {
		return (List<Integrante>) repository.findAll();
	}

	@Transactional
	@RequestMapping(value = "/others/{repositorioId}", method = RequestMethod.GET)
	public List<Integrante> listarOthes(@PathVariable("repositorioId") Long repositorioId) {

		List<Integrante> integrantes = new ArrayList<>();
		List<Integrante> findAll = (List<Integrante>) repository.findAll();

		Repositorio repo = repositorioRepository.findOne(repositorioId);
		boolean alreadyAdded = false;

		for ( Integrante integrante : findAll ) {

			alreadyAdded = false;

			for ( Integrante i : repo.getTime() ) {

				if (integrante.getId() == i.getId() ) {
					alreadyAdded = true;
					break;
				}
			}

			if( alreadyAdded == false) {
				integrantes.add(integrante);
			}
		}

		Collections.sort(integrantes, ComparatorPorNome);

		return integrantes;
	}

	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Integrante recuperar( @PathVariable("id") Long id) {
		return repository.findOne(id);
	}

	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public List<Integrante> excluir( @PathVariable("id") Long id) {

		List<Integrante> findAll = new ArrayList<Integrante>();

		try {
			repository.delete(id);
			findAll = (List<Integrante>) repository.findAll();
		} catch (Exception e) {
			throw new EmailOverviewException(HttpStatus.PRECONDITION_FAILED, "Este integrante está associado a um repositório.");
		}

		return findAll;
	}

	@RequestMapping(value="/tokens", method=RequestMethod.GET)
	@Transactional(readOnly=true)
	public List<IntegranteSparksDTO> listarTokensFuncionarios(){
		List<Integrante> integrantes = (List<Integrante>) repository.findAll();
		Map<Integrante, Integer> tokensByUser = new LinkedHashMap<Integrante, Integer>();
		integrantes.forEach(i -> tokensByUser.put(i, 0));

		List<FaseProjeto> fases = faseProjetoRepository.findByStatusIn(Arrays.asList(StatusFase.EM_ANDAMENTO, StatusFase.ENTREGUE));
		for(FaseProjeto fase : fases){
			List<EntregaSprint> entregas = entregaSprintRepository.findByFaseAndEntregueTrue(fase);
			for(EntregaSprint entrega : entregas){
				Set<Integrante> entregaIntegrantes = entrega.getIntegrantes();
				for(Integrante i : entregaIntegrantes){
					Integer tokens = tokensByUser.get(i);
					if(fase.getStatus().equals(StatusFase.EM_ANDAMENTO)){
						tokens++;
					} else {
						tokens = tokens+2;
					}

					tokensByUser.put(i, tokens);
				}
			}
		}

		List<IntegranteSparksDTO> dtos = new ArrayList<IntegranteSparksDTO>();
		for(Integrante integrante : tokensByUser.keySet()){
			IntegranteSparksDTO dto = new IntegranteSparksDTO();
			dto.setId(integrante.getId());
			dto.setEmail(integrante.getEmail());
			dto.setNome(integrante.getNome());
			dto.setSparks(tokensByUser.get(integrante));
			dto.setSparksRestantes(listarTokensRestantesFuncionarios(integrante.getId()));
			dtos.add(dto);
		}

		dtos.sort(new IntegranteSparkComparator());

		return dtos;
	}

	public static Comparator<Integrante> ComparatorPorNome = new Comparator<Integrante>() {
		public int compare(Integrante inte, Integrante inte2) {
			return inte2.getNome().toUpperCase().compareTo(inte2.getNome().toUpperCase());
		}
	};

	@RequestMapping(value="/tokens/{id}", method=RequestMethod.GET)
	@Transactional(readOnly=true)
	public IntegranteSparksDTO listarTokensFuncionariosById(@PathVariable("id") Long id){
		Integrante integranteSparks = repository.findOne(id);

		IntegranteSparksDTO dto = new IntegranteSparksDTO();
		dto.setId(integranteSparks.getId());
		dto.setNome(integranteSparks.getNome());
		
		Integer tokens = integranteService.retornaQuantidadeTokensGanha(integranteSparks);
		dto.setSparks(tokens);

		return dto;
	}
	
	@RequestMapping(value="/tokens/restantes/{id}", method=RequestMethod.GET)
	@Transactional(readOnly=true)
	public Integer listarTokensRestantesFuncionarios(@PathVariable("id") Long id){
		Integrante integranteSparks = repository.findOne(id);

		IntegranteSparksDTO dto = new IntegranteSparksDTO();
		dto.setId(integranteSparks.getId());
		dto.setNome(integranteSparks.getNome());
		
		Integer tokens = integranteService.retornaQuantidadeTokensAtual(integranteSparks);
		//dto.setSparks(tokens);

		return tokens;
	}

}
