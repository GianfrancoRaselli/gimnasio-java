package Entidades;

import java.util.Date;

public class Inscripcion 
{
	public enum Estados{
		Inscripto(1), Presente(2), Ausente(3), Cancelada(4);
		
		private int nroEstado;
		
		private Estados(int nroEstado)
		{
			this.nroEstado = nroEstado;
		}
		
		public int getNroEstado()
		{
			return this.nroEstado;
		}
	}
	
	public Inscripcion() {
		super();
	}
	private Persona persona;
	private ClasePersonalizada clasePersonalizada;
	private Date fechaHoraInscripcion;
	private Estados estado;
	@Override
	public String toString() {
		return "Inscripcion [persona=" + persona + ", clasePersonalizada=" + clasePersonalizada
				+ ", fechaHoraInscripcion=" + fechaHoraInscripcion + ", estado=" + estado + "]";
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public ClasePersonalizada getClasePersonalizada() {
		return clasePersonalizada;
	}
	public void setClasePersonalizada(ClasePersonalizada clasePersonalizada) {
		this.clasePersonalizada = clasePersonalizada;
	}
	public Date getFechaHoraInscripcion() {
		return fechaHoraInscripcion;
	}
	public void setFechaHoraInscripcion(Date fechaHoraInscripcion) {
		this.fechaHoraInscripcion = fechaHoraInscripcion;
	}
	public Estados getEstado() {
		return estado;
	}
	public void setEstado(Estados estado) {
		this.estado = estado;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clasePersonalizada == null) ? 0 : clasePersonalizada.hashCode());
		result = prime * result + ((fechaHoraInscripcion == null) ? 0 : fechaHoraInscripcion.hashCode());
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
		Inscripcion other = (Inscripcion) obj;
		if (clasePersonalizada == null) {
			if (other.clasePersonalizada != null)
				return false;
		} else if (!clasePersonalizada.equals(other.clasePersonalizada))
			return false;
		if (fechaHoraInscripcion == null) {
			if (other.fechaHoraInscripcion != null)
				return false;
		} else if (!fechaHoraInscripcion.equals(other.fechaHoraInscripcion))
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		return true;
	}
}
