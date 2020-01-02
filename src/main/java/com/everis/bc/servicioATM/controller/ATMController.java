package com.everis.bc.servicioATM.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bc.servicioATM.model.Movimientos;
import com.everis.bc.servicioATM.service.ATMService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ATMController {

	@Autowired
	private ATMService service;
	
	@PostMapping("/saveDepositoATMInterbankAhorro")
	public Mono<Movimientos> saveDepositoATMInterbankAhorro(@RequestBody Movimientos movimiento){
		return service.saveDepositoAhorro(movimiento);
	}
	
	@PostMapping("/saveRetiroATMInterbankAhorro")
	public Mono<Movimientos> saveRetiroATMInterbankAhorro(@RequestBody Movimientos movimiento){
		return service.saveRetiroAhorro(movimiento);
	}
	
	@PostMapping("/saveDepositoATMInterbankPcorriente")
	public Mono<Movimientos> saveDepositoATMInterbankPcorriente(@RequestBody Movimientos movimiento){
		return service.saveDepositoPcorriente(movimiento);
	}
	
	@PostMapping("/saveRetiroATMInterbankPcorriente")
	public Mono<Movimientos> saveRetiroATMInterbankPcorriente(@RequestBody Movimientos movimiento){
		return service.saveRetiroPcorriente(movimiento);
	}
	
	@PostMapping("/saveDepositoATMInterbankEcorriente")
	public Mono<Movimientos> saveDepositoATMInterbankEcorriente(@RequestBody Movimientos movimiento){
		return service.saveDepositoEcorriente(movimiento);
	}
	
	@PostMapping("/saveRetiroATMInterbankEcorriente")
	public Mono<Movimientos> saveRetiroATMInterbankEcorriente(@RequestBody Movimientos movimiento){
		return service.saveRetiroEcorriente(movimiento);
	}
	
	@PostMapping("/saveDepositoATMInterbankVip")
	public Mono<Movimientos> saveDepositoATMInterbankVip(@RequestBody Movimientos movimiento){
		return service.saveDepositoVip(movimiento);
	}
	
	@PostMapping("/saveRetiroATMInterbankEcorrienteVip")
	public Mono<Movimientos> saveRetiroATMInterbankEcorrienteVip(@RequestBody Movimientos movimiento){
		return service.saveRetiroVip(movimiento);
	}
	

}
