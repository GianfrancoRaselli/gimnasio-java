package Entidades;

import java.util.Date;

public class PrecioGimnasio 
{
	public PrecioGimnasio() {
		super();
	}
	private Date fechaHoraDesde;
	private double precio;
	@Override
	public String toString() {
		return "PrecioGimnasio [fechaHoraDesde=" + fechaHoraDesde + ", precio=" + precio + "]";
	}
	public Date getFechaHoraDesde() {
		return fechaHoraDesde;
	}
	public void setFechaHoraDesde(Date fechaHoraDesde) {
		this.fechaHoraDesde = fechaHoraDesde;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaHoraDesde == null) ? 0 : fechaHoraDesde.hashCode());
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
		PrecioGimnasio other = (PrecioGimnasio) obj;
		if (fechaHoraDesde == null) {
			if (other.fechaHoraDesde != null)
				return false;
		} else if (!fechaHoraDesde.equals(other.fechaHoraDesde))
			return false;
		return true;
	}
}
