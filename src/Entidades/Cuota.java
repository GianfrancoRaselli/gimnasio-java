package Entidades;

import java.util.Date;

public class Cuota 
{
	public Cuota() {
		super();
	}
	private Persona persona;
	private int anio;
	private int mes;
	private double total;
	private Date fechaHoraPago;
	@Override
	public String toString() {
		return "Cuota [persona=" + persona + ", anio=" + anio + ", mes=" + mes + ", total=" + total + ", fechaHoraPago="
				+ fechaHoraPago + "]";
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Date getFechaHoraPago() {
		return fechaHoraPago;
	}
	public void setFechaHoraPago(Date fechaHoraPago) {
		this.fechaHoraPago = fechaHoraPago;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anio;
		result = prime * result + mes;
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuota other = (Cuota) obj;
		if (anio != other.anio)
			return false;
		if (mes != other.mes)
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		return true;
	}
}
