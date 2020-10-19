package Entidades;

import java.util.Collection;

public class Ciudad 
{
	public Ciudad() {
		super();
	}
	private String codPostal;
	private String descripcion;
	private Provincia provincia;
	private Collection<Persona> personas;
	private Collection<Sucursal> sucursales;
	@Override
	public String toString() {
		return "Ciudad [codPostal=" + codPostal + ", descripcion=" + descripcion + ", provincia=" + provincia + "]";
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Provincia getProvincia() {
		return provincia;
	}
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	public Collection<Persona> getPersonas() {
		return personas;
	}
	public void setPersonas(Collection<Persona> personas) {
		this.personas = personas;
	}
	public Collection<Sucursal> getSucursales() {
		return sucursales;
	}
	public void setSucursales(Collection<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}
	public void agregarPersona(Persona persona) {
		personas.add(persona);
	}
	public void agregarSucursal(Sucursal sucursal) {
		sucursales.add(sucursal);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codPostal == null) ? 0 : codPostal.hashCode());
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
		Ciudad other = (Ciudad) obj;
		if (codPostal == null) {
			if (other.codPostal != null)
				return false;
		} else if (!codPostal.equals(other.codPostal))
			return false;
		return true;
	}
}
