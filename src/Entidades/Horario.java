package Entidades;

import java.sql.Time;

public class Horario 
{
	public Horario() {
		super();
	}
	private Dia dia;
	private Sucursal sucursal;
	private Time idHoraDesde;
	private Time horaDesde;
	private Time horaHasta;
	@Override
	public String toString() {
		return "Horario [dia=" + dia + ", sucursal=" + sucursal + ", horaDesde=" + horaDesde + ", horaHasta="
				+ horaHasta + "]";
	}
	public Dia getDia() {
		return dia;
	}
	public void setDia(Dia dia) {
		this.dia = dia;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Time getHoraDesde() {
		return horaDesde;
	}
	public void setHoraDesde(Time horaDesde) {
		this.horaDesde = horaDesde;
	}
	public Time getIdHoraDesde() {
		return idHoraDesde;
	}
	public void setIdHoraDesde(Time idHoraDesde) {
		this.idHoraDesde = idHoraDesde;
	}
	public Time getHoraHasta() {
		return horaHasta;
	}
	public void setHoraHasta(Time horaHasta) {
		this.horaHasta = horaHasta;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dia == null) ? 0 : dia.hashCode());
		result = prime * result + ((horaDesde == null) ? 0 : horaDesde.hashCode());
		result = prime * result + ((sucursal == null) ? 0 : sucursal.hashCode());
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
		Horario other = (Horario) obj;
		if (dia == null) {
			if (other.dia != null)
				return false;
		} else if (!dia.equals(other.dia))
			return false;
		if (horaDesde == null) {
			if (other.horaDesde != null)
				return false;
		} else if (!horaDesde.equals(other.horaDesde))
			return false;
		if (sucursal == null) {
			if (other.sucursal != null)
				return false;
		} else if (!sucursal.equals(other.sucursal))
			return false;
		return true;
	}
}
