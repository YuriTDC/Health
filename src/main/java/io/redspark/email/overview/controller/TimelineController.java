package io.redspark.email.overview.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import io.redspark.email.overview.dto.TimelineDTO;
import io.redspark.email.overview.entity.EntregaSprint;
import io.redspark.email.overview.entity.FaseProjeto;
import io.redspark.email.overview.entity.TrocaPremio;
import io.redspark.email.overview.enums.StatusFase;
import io.redspark.email.overview.repository.EntregaSprintRepository;
import io.redspark.email.overview.repository.FaseProjetoRepository;
import io.redspark.email.overview.repository.TrocaPremioRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/timeline")
public class TimelineController {

    @Autowired
    private TrocaPremioRepository trocaPremioRepository;
    
    @Autowired
    private EntregaSprintRepository entregaSprintRepository;
    
    @Autowired
    private FaseProjetoRepository faseProjetoRepository;
    
    
    @RequestMapping(value = "/timeline", method=GET)
    @Transactional(readOnly = true)
    public Map<String, List<TimelineDTO>> list(){
        
            List<TrocaPremio> trocaPremio = (List<TrocaPremio>) trocaPremioRepository.findAll();
            List<FaseProjeto> faseProjeto = (List<FaseProjeto>) faseProjetoRepository.findByStatusIn(Arrays.asList(StatusFase.ENTREGUE, StatusFase.NAO_ENTREGUE));
            List<EntregaSprint> entregaSprint = (List<EntregaSprint>) entregaSprintRepository.findAll();
            
            List<TimelineDTO> timelineReal = new ArrayList<TimelineDTO>();
            
            for (FaseProjeto fase : faseProjeto){
                timelineReal.add(new TimelineDTO(fase));
            }
            
            for (EntregaSprint entrega: entregaSprint){
                timelineReal.add(new TimelineDTO(entrega));
            }
            
            for (TrocaPremio troca: trocaPremio){
                timelineReal.add(new TimelineDTO(troca));
            }
            
            Collections.sort(timelineReal, ComparatorPorData);
            
            Map<String, List<TimelineDTO>> timeline = new LinkedHashMap<String, List<TimelineDTO>>();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for(TimelineDTO t : timelineReal){
                String formatDate = t.getDataCriacao().format(format);
                List<TimelineDTO> list = timeline.get(formatDate);
                if(list == null){
                    list = new ArrayList<TimelineDTO>();
                }
                list.add(t);
                timeline.put(formatDate, list);
            }
            
            return timeline;
        }
    
    public static Comparator<TimelineDTO> ComparatorPorData = new Comparator<TimelineDTO>() {
       public int compare(TimelineDTO o1, TimelineDTO o2) {
           return o1.getDataCriacao().compareTo(o2.getDataCriacao());
       }
   };
    }