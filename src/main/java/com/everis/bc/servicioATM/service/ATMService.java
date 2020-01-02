package com.everis.bc.servicioATM.service;


import com.everis.bc.servicioATM.model.Movimientos;
import reactor.core.publisher.Mono;

public interface ATMService {
	
    public Mono<Movimientos> saveDepositoAhorro(Movimientos mov);
	
	public Mono<Movimientos> saveRetiroAhorro(Movimientos mov);
	
public Mono<Movimientos> saveDepositoPcorriente(Movimientos mov);
	
	public Mono<Movimientos> saveRetiroPcorriente(Movimientos mov);
	
public Mono<Movimientos> saveDepositoEcorriente(Movimientos mov);
	
	public Mono<Movimientos> saveRetiroEcorriente(Movimientos mov);
	
public Mono<Movimientos> saveDepositoVip(Movimientos mov);
	
	public Mono<Movimientos> saveRetiroVip(Movimientos mov);
}
