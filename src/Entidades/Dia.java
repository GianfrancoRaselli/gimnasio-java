package Entidades;

import java.util.Collection;

public class Dia 
{
	public Dia() {
		super();
	}
	private int nroDia;
	private String descripcion;
	private Collection<Horario> horarios;
	@Override
	public String toString() {
		return "Dia [nroDia=" + nroDia + ", descripcion=" + descripcion + "]";
	}
	public int getNroDia() {
		return nroDia;
	}
	public void setNroDia(int nroDia) {
		this.nroDia = nroDia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Collection<Horario> getHorarios() {
		return horarios;
	}
	public void setHorarios(Collection<Horario> horarios) {
		this.horarios = horarios;
	}
	public void agregarHorario(Horario horario) {
		horarios.add(horario);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nroDia;
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
		Dia other = (Dia) obj;
		if (nroDia != other.nroDia)
			return false;
		return true;
	}
}
