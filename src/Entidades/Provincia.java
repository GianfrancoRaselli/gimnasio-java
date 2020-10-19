package Entidades;

import java.util.Collection;

public class Provincia 
{
	public Provincia() {
		super();
	}
	private String nombreProvincia;
	private Collection<Ciudad> ciudades;
	@Override
	public String toString() {
		return "Provincia [nombreProvincia=" + nombreProvincia + "]";
	}
	public String getNombreProvincia() {
		return nombreProvincia;
	}
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	public Collection<Ciudad> getCiudades() {
		return ciudades;
	}
	public void setCiudades(Collection<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}
	public void agregarCiudad(Ciudad ciudad) {
		ciudades.add(ciudad);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreProvincia == null) ? 0 : nombreProvincia.hashCode());
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
		Provincia other = (Provincia) obj;
		if (nombreProvincia == null) {
			if (other.nombreProvincia != null)
				return false;
		} else if (!nombreProvincia.equals(other.nombreProvincia))
			return false;
		return true;
	}
}
