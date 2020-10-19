package Entidades;

import javax.persistence.*;

@Entity
@Table(name="niveles_usuarios")
public class NivelUsuario 
{
	public NivelUsuario() {
		super();
	}
	
	@Id
	@Column(name="nro_nivel")
	private int nroNivel;
	
	@Column(name="descripcion")
	private String descripcion;

	//private Collection<Usuario> usuarios;
	@Override
	public String toString() {
		return "NivelUsuario [nroNivel=" + nroNivel + ", descripcion=" + descripcion + "]";
	}
	public int getNroNivel() {
		return nroNivel;
	}
	public void setNroNivel(int nroNivel) {
		this.nroNivel = nroNivel;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/*public Collection<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public void agregarUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nroNivel;
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
		NivelUsuario other = (NivelUsuario) obj;
		if (nroNivel != other.nroNivel)
			return false;
		return true;
	}
}
