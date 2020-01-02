package com.everis.bc.servicioATM.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="c_movimientos")
public class Movimientos {

	@Id
	private String id;
	@NotNull
	private String bankcode;
	@NotNull
	private String nro_cuenta;
	@NotNull
	private String cuentaTipo;
	@NotNull
	private String descripcion;
	@NotNull
	private double monto;
	@NotNull
	private double comision;
	@NotNull
	private int movesxmonth;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	public int getMovesxmonth() {
		return movesxmonth;
	}
	public void setMovesxmonth(int movesxmonth) {
		this.movesxmonth = movesxmonth;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public double getComision() {
		return comision;
	}
	public void setComision(double comision) {
		this.comision = comision;
	}
	public String getCuentaTipo() {
		return cuentaTipo;
	}
	public void setCuentaTipo(String cuentaTipo) {
		this.cuentaTipo = cuentaTipo;
	}
	public String getNro_cuenta() {
		return nro_cuenta;
	}
	public void setNro_cuenta(String nro_cuenta) {
		this.nro_cuenta = nro_cuenta;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
