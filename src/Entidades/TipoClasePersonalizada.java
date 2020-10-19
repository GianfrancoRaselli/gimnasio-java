package Entidades;

import java.util.Collection;

public class TipoClasePersonalizada 
{
	public TipoClasePersonalizada() {
		super();
	}
	private int codTipoClasePersonalizada;
	private String nombre;
	private String descripcion;
	private Collection<ClasePersonalizada> clasesPersonalizadas;
	@Override
	public String toString() {
		return "TipoClasePersonalizada [codTipoClasePersonalizada=" + codTipoClasePersonalizada + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + "]";
	}
	public int getCodTipoClasePersonalizada() {
		return codTipoClasePersonalizada;
	}
	public void setCodTipoClasePersonalizada(int codTipoClasePersonalizada) {
		this.codTipoClasePersonalizada = codTipoClasePersonalizada;
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
	public Collection<ClasePersonalizada> getClasesPersonalizadas() {
		return clasesPersonalizadas;
	}
	public void setClasesPersonalizadas(Collection<ClasePersonalizada> clasesPersonalizadas) {
		this.clasesPersonalizadas = clasesPersonalizadas;
	}
	public void agregarClasePersonalizada(ClasePersonalizada clasePersonalizada) {
		clasesPersonalizadas.add(clasePersonalizada);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codTipoClasePersonalizada;
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
		TipoClasePersonalizada other = (TipoClasePersonalizada) obj;
		if (codTipoClasePersonalizada != other.codTipoClasePersonalizada)
			return false;
		return true;
	}
}
