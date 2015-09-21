package io.redspark.email.overview.repository;

import io.redspark.email.overview.entity.Usuario;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	Usuario findByEmail(String email);

	Usuario findByUsuario(String name);

}
