package io.redspark.email.overview.test.builders;

import io.redspark.email.overview.entity.Integrante;
import io.redspark.email.overview.entity.Premio;
import io.redspark.email.overview.entity.TrocaPremio;

import java.time.LocalDateTime;

import aleph.AbstractBuilder;

public class TrocaPremioBuilder extends AbstractBuilder<TrocaPremio>{
	
	public static TrocaPremioBuilder trocaPremio(Integrante integrante, Premio premio){
		return new TrocaPremioBuilder()
		.integrante(integrante)
		.premio(premio)
		.dataTroca(LocalDateTime.now());
			
	}

	
	public TrocaPremioBuilder integrante(Integrante integrante){
		return set("integrante", integrante);
		
	}
	
	public TrocaPremioBuilder premio(Premio premio){
		return set("premio" , premio);
	}
	
	public TrocaPremioBuilder dataTroca(LocalDateTime dataTroca){
		return set("dataTroca", dataTroca);
	}



	
}

