package Entidades;

import java.util.Collection;

public class TipoEjercicio 
{
	public TipoEjercicio() {
		super();
	}
	private int codTipoEjercicio;
	private String nombre;
	private String descripcion;
	private Collection<Ejercicio> ejercicios;
	@Override
	public String toString() {
		return "TipoEjercicio [codTipoEjercicio=" + codTipoEjercicio + ", nombre=" + nombre + ", descripcion="
				+ descripcion + "]";
	}
	public int getCodTipoEjercicio() {
		return codTipoEjercicio;
	}
	public void setCodTipoEjercicio(int codTipoEjercicio) {
		this.codTipoEjercicio = codTipoEjercicio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Collection<Ejercicio> getEjercicios() {
		return ejercicios;
	}
	public void setEjercicios(Collection<Ejercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}
	public void agregarEjercicio(Ejercicio ejercicio) {
		ejercicios.add(ejercicio);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codTipoEjercicio;
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
		TipoEjercicio other = (TipoEjercicio) obj;
		if (codTipoEjercicio != other.codTipoEjercicio)
			return false;
		return true;
	}
}
