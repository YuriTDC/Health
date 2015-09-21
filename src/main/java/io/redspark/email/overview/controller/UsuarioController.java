package io.redspark.email.overview.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import io.redspark.email.overview.dto.UsuarioDTO;
import io.redspark.email.overview.entity.Usuario;
import io.redspark.email.overview.repository.UsuarioRepository;
import io.redspark.email.overview.security.EmailOverviewSecurityContext;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@RequestMapping(method = GET)
	public UsuarioDTO recupera(HttpServletResponse response) throws IOException {
		
		Usuario usuario = EmailOverviewSecurityContext.getLoggedUser();
		
		if (usuario == null) {
			response.sendError(HttpStatus.UNAUTHORIZED.value());
		}
		
		return new UsuarioDTO( usuario );
	}

}
