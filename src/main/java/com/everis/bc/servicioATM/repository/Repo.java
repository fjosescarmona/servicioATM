package com.everis.bc.servicioATM.repository;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.bc.servicioATM.model.Movimientos;

import reactor.core.publisher.Mono;
import java.lang.String;

public interface Repo extends ReactiveMongoRepository<Movimientos, String>{
	@Query("{ 'nro_cuenta': ?0 }")
	public Mono<Movimientos> findByNro_cuenta(String nro_cuenta);
	
}
