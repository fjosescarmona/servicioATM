package com.everis.bc.servicioATM.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.bc.servicioATM.model.Movimientos;
import com.everis.bc.servicioATM.repository.Repo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ATMImpl implements ATMService {

	// private WebClient client;
	@Autowired
	private Repo repo1;
	@Autowired
	@Qualifier("vip")
	private WebClient vip;
	@Autowired
	@Qualifier("pcorriente")
	private WebClient pcorriente;
	@Autowired
	@Qualifier("ecorriente")
	private WebClient ecorriente;
	@Autowired
	@Qualifier("ahorro")
	private WebClient ahorro;
	@Autowired
	@Qualifier("tc")
	private WebClient tc;

	@Value("${valores.comision}")
	private double comision;
	@Value("${valores.movesxmonth}")
	private int movesxmonth;
	@Value("${valores.bankcode}")
	private String bankcode;

	public Mono<Movimientos> saveDepositoAhorro(Movimientos mov) {
		// TODO Auto-generated method stub
		return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			if(cta.getFecha().getMonth() == mov.getFecha().getMonth() && cta.getMovesxmonth() > 0) {
				cta.setFecha(mov.getFecha());
				cta.setMovesxmonth(cta.getMovesxmonth()-1);
			}else {
				// -si el mes de la transaccion es distinto reinicia la cantidad de movimientos
				// por mes-//
					if (cta.getFecha().getMonth() != mov.getFecha().getMonth()) {
					cta.setMovesxmonth(1);
					cta.setFecha(mov.getFecha());
					// --si no tiene movimientos disponibles en el mes aplica el cobro de
					// comision--//
					} else {
						mov.setComision(mov.getComision()+comision);
						cta.setFecha(mov.getFecha());

					} 
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return ahorro.post().uri("/saveDepositoAhorro").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.save(cta);
						} else {
							return Mono.just(new Movimientos());
						}

					});
		}).switchIfEmpty(repo1.save(mov).flatMap(ctas->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return ahorro.post().uri("/saveDepositoAhorro").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
								cta.setMovesxmonth(1);
								return repo1.save(cta);
							});
							
						} else {
							return Mono.just(new Movimientos());
						}
					});
		}));

	}

	@Override
	public Mono<Movimientos> saveRetiroAhorro(Movimientos mov) {
		// TODO Auto-generated method stub
		return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			if(cta.getFecha().getMonth() == mov.getFecha().getMonth() && cta.getMovesxmonth() > 0) {
				cta.setFecha(mov.getFecha());
				cta.setMovesxmonth(cta.getMovesxmonth()-1);
			}else {
				// -si el mes de la transaccion es distinto reinicia la cantidad de movimientos
				// por mes-//
					if (cta.getFecha().getMonth() != mov.getFecha().getMonth()) {
					cta.setMovesxmonth(1);
					cta.setFecha(mov.getFecha());
					// --si no tiene movimientos disponibles en el mes aplica el cobro de
					// comision--//
					} else {
						mov.setComision(mov.getComision()+comision);
						cta.setFecha(mov.getFecha());

					} 
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return ahorro.post().uri("/saveRetiroAhorro").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.save(cta);
						} else {
							return Mono.just(new Movimientos());
						}

					});
		}).switchIfEmpty(repo1.save(mov).flatMap(ctas->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return ahorro.post().uri("/saveRetiroAhorro").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
								cta.setMovesxmonth(1);
								return repo1.save(cta);
							});
							
						} else {
							return Mono.just(new Movimientos());
						}
					});
		}));
	}

	@Override
	public Mono<Movimientos> saveDepositoPcorriente(Movimientos mov) {
		// TODO Auto-generated method stub
		return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			if(cta.getFecha().getMonth() == mov.getFecha().getMonth() && cta.getMovesxmonth() > 0) {
				cta.setFecha(mov.getFecha());
				cta.setMovesxmonth(cta.getMovesxmonth()-1);
			}else {
				// -si el mes de la transaccion es distinto reinicia la cantidad de movimientos
				// por mes-//
					if (cta.getFecha().getMonth() != mov.getFecha().getMonth()) {
					cta.setMovesxmonth(1);
					cta.setFecha(mov.getFecha());
					// --si no tiene movimientos disponibles en el mes aplica el cobro de
					// comision--//
					} else {
						mov.setComision(mov.getComision()+comision);
						cta.setFecha(mov.getFecha());

					} 
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return pcorriente.post().uri("/saveDepositoPcorriente").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.save(cta);
						} else {
							return Mono.just(new Movimientos());
						}

					});
		}).switchIfEmpty(repo1.save(mov).flatMap(ctas->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return pcorriente.post().uri("/saveDepositoPcorriente").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
								cta.setMovesxmonth(1);
								return repo1.save(cta);
							});
							
						} else {
							return Mono.just(new Movimientos());
						}
					});
		}));
	}

	@Override
	public Mono<Movimientos> saveRetiroPcorriente(Movimientos mov) {
		// TODO Auto-generated method stub
		return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			if(cta.getFecha().getMonth() == mov.getFecha().getMonth() && cta.getMovesxmonth() > 0) {
				cta.setFecha(mov.getFecha());
				cta.setMovesxmonth(cta.getMovesxmonth()-1);
			}else {
				// -si el mes de la transaccion es distinto reinicia la cantidad de movimientos
				// por mes-//
					if (cta.getFecha().getMonth() != mov.getFecha().getMonth()) {
					cta.setMovesxmonth(1);
					cta.setFecha(mov.getFecha());
					// --si no tiene movimientos disponibles en el mes aplica el cobro de
					// comision--//
					} else {
						mov.setComision(mov.getComision()+comision);
						cta.setFecha(mov.getFecha());

					} 
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return pcorriente.post().uri("/saveRetiroPcorriente").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.save(cta);
						} else {
							return Mono.just(new Movimientos());
						}

					});
		}).switchIfEmpty(repo1.save(mov).flatMap(ctas->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return pcorriente.post().uri("/saveRetiroPcorriente").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
								cta.setMovesxmonth(1);
								return repo1.save(cta);
							});
							
						} else {
							return Mono.just(new Movimientos());
						}
					});
		}));
	}

	@Override
	public Mono<Movimientos> saveDepositoEcorriente(Movimientos mov) {
		// TODO Auto-generated method stub
		return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			if(cta.getFecha().getMonth() == mov.getFecha().getMonth() && cta.getMovesxmonth() > 0) {
				cta.setFecha(mov.getFecha());
				cta.setMovesxmonth(cta.getMovesxmonth()-1);
			}else {
				// -si el mes de la transaccion es distinto reinicia la cantidad de movimientos
				// por mes-//
					if (cta.getFecha().getMonth() != mov.getFecha().getMonth()) {
					cta.setMovesxmonth(1);
					cta.setFecha(mov.getFecha());
					// --si no tiene movimientos disponibles en el mes aplica el cobro de
					// comision--//
					} else {
						mov.setComision(mov.getComision()+comision);
						cta.setFecha(mov.getFecha());

					} 
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return ecorriente.post().uri("/saveDepositoEcorriente").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.save(cta);
						} else {
							return Mono.just(new Movimientos());
						}

					});
		}).switchIfEmpty(repo1.save(mov).flatMap(ctas->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return ecorriente.post().uri("/saveDepositoEcorriente").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
								cta.setMovesxmonth(1);
								return repo1.save(cta);
							});
							
						} else {
							return Mono.just(new Movimientos());
						}
					});
		}));
	}

	@Override
	public Mono<Movimientos> saveRetiroEcorriente(Movimientos mov) {
		// TODO Auto-generated method stub
		return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			if(cta.getFecha().getMonth() == mov.getFecha().getMonth() && cta.getMovesxmonth() > 0) {
				cta.setFecha(mov.getFecha());
				cta.setMovesxmonth(cta.getMovesxmonth()-1);
			}else {
				// -si el mes de la transaccion es distinto reinicia la cantidad de movimientos
				// por mes-//
					if (cta.getFecha().getMonth() != mov.getFecha().getMonth()) {
					cta.setMovesxmonth(1);
					cta.setFecha(mov.getFecha());
					// --si no tiene movimientos disponibles en el mes aplica el cobro de
					// comision--//
					} else {
						mov.setComision(mov.getComision()+comision);
						cta.setFecha(mov.getFecha());

					} 
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return ecorriente.post().uri("/saveRetiroEcorriente").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.save(cta);
						} else {
							return Mono.just(new Movimientos());
						}

					});
		}).switchIfEmpty(repo1.save(mov).flatMap(ctas->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return ecorriente.post().uri("/saveRetiroEcorriente").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
								cta.setMovesxmonth(1);
								return repo1.save(cta);
							});
							
						} else {
							return Mono.just(new Movimientos());
						}
					});
		}));
	}

	@Override
	public Mono<Movimientos> saveDepositoVip(Movimientos mov) {
		// TODO Auto-generated method stub
		return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			if(cta.getFecha().getMonth() == mov.getFecha().getMonth() && cta.getMovesxmonth() > 0) {
				cta.setFecha(mov.getFecha());
				cta.setMovesxmonth(cta.getMovesxmonth()-1);
			}else {
				// -si el mes de la transaccion es distinto reinicia la cantidad de movimientos
				// por mes-//
					if (cta.getFecha().getMonth() != mov.getFecha().getMonth()) {
					cta.setMovesxmonth(1);
					cta.setFecha(mov.getFecha());
					// --si no tiene movimientos disponibles en el mes aplica el cobro de
					// comision--//
					} else {
						mov.setComision(mov.getComision()+comision);
						cta.setFecha(mov.getFecha());

					} 
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return vip.post().uri("/saveDepositoCorrienteVip").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.save(cta);
						} else {
							return Mono.just(new Movimientos());
						}

					});
		}).switchIfEmpty(repo1.save(mov).flatMap(ctas->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return vip.post().uri("/saveDepositoCorrienteVip").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
								cta.setMovesxmonth(1);
								return repo1.save(cta);
							});
							
						} else {
							return Mono.just(new Movimientos());
						}
					});
		}));
	}

	@Override
	public Mono<Movimientos> saveRetiroVip(Movimientos mov) {
		// TODO Auto-generated method stub
		return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			if(cta.getFecha().getMonth() == mov.getFecha().getMonth() && cta.getMovesxmonth() > 0) {
				cta.setFecha(mov.getFecha());
				cta.setMovesxmonth(cta.getMovesxmonth()-1);
			}else {
				// -si el mes de la transaccion es distinto reinicia la cantidad de movimientos
				// por mes-//
					if (cta.getFecha().getMonth() != mov.getFecha().getMonth()) {
					cta.setMovesxmonth(1);
					cta.setFecha(mov.getFecha());
					// --si no tiene movimientos disponibles en el mes aplica el cobro de
					// comision--//
					} else {
						mov.setComision(mov.getComision()+comision);
						cta.setFecha(mov.getFecha());

					} 
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return vip.post().uri("/saveRetiroCorrienteVip").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.save(cta);
						} else {
							return Mono.just(new Movimientos());
						}

					});
		}).switchIfEmpty(repo1.save(mov).flatMap(ctas->{
			if(!mov.getBankcode().equals(bankcode)) {
				mov.setComision(mov.getComision()+comision);
			}
			
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nro_cuenta", mov.getNro_cuenta());
			params.put("descripcion", mov.getDescripcion());
			params.put("monto", mov.getMonto());
			params.put("fecha", mov.getFecha());
			params.put("comision", mov.getComision());

			return vip.post().uri("/saveRetiroCorrienteVip").accept(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(params)).retrieve().bodyToMono(Movimientos.class)
					.flatMap(ptdc -> {

						if (!ptdc.getNro_cuenta().equals(null)) {
							return repo1.findByNro_cuenta(mov.getNro_cuenta()).flatMap(cta->{
								cta.setMovesxmonth(1);
								return repo1.save(cta);
							});
							
						} else {
							return Mono.just(new Movimientos());
						}
					});
		}));
	}

}
