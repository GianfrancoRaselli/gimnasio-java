package Entidades;

import java.sql.Timestamp;

public class Horario 
{
	public Horario() {
		super();
	}
	private Dia dia;
	private Sucursal sucursal;
	private Timestamp idHoraDesde;
	private Timestamp horaDesde;
	private Timestamp horaHasta;
	private String idHoraDesdeString;
	private String horaDesdeString;
	private String horaHastaString;
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
	public Timestamp getHoraDesde() {
		return horaDesde;
	}
	public void setHoraDesde(Timestamp horaDesde) {
		this.horaDesde = horaDesde;
	}
	public Timestamp getIdHoraDesde() {
		return idHoraDesde;
	}
	public void setIdHoraDesde(Timestamp idHoraDesde) {
		this.idHoraDesde = idHoraDesde;
	}
	public Timestamp getHoraHasta() {
		return horaHasta;
	}
	public void setHoraHasta(Timestamp horaHasta) {
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
	public String getIdHoraDesdeString() {
		return idHoraDesdeString;
	}
	public void setIdHoraDesdeString(String idHoraDesdeString) {
		this.idHoraDesdeString = idHoraDesdeString;
	}
	public String getHoraDesdeString() {
		return horaDesdeString;
	}
	public void setHoraDesdeString(String horaDesdeString) {
		this.horaDesdeString = horaDesdeString;
	}
	public String getHoraHastaString() {
		return horaHastaString;
	}
	public void setHoraHastaString(String horaHastaString) {
		this.horaHastaString = horaHastaString;
	}
}
