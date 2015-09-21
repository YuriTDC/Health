package io.redspark.email.overview.repository;

import io.redspark.email.overview.entity.Cliente;

import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}