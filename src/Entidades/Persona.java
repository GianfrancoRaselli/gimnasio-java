package Entidades;

import java.util.Collection;

public class Persona 
{
	public enum Tipos{
		Administrativo(1), Entrenador(2), Cliente(3);
		
		private int nroTipo;
		
		private Tipos(int nroTipo)
		{
			this.nroTipo = nroTipo;
		}
		
		public int getNroTipo()
		{
			return this.nroTipo;
		}
	}
	
	public Persona() {
		super();
	}
	private String id;
	private String dni;
	private String nombreApellido;
	private String telefono;
	private String email;
	private String direccion;
	private Tipos tipo;
	private Ciudad ciudad;
	private Usuario usuario;
	private Collection<ClasePersonalizada> clasesPersonalizadas;
	private Collection<Inscripcion> inscripciones;
	private Collection<Cuota> cuotas;
	private Collection<Asistencia> asistencias;
	@Override
	public String toString() {
		return "Persona [dni=" + dni + ", nombreApellido=" + nombreApellido + ", telefono=" + telefono + ", email="
				+ email + ", direccion=" + direccion + ", tipo=" + tipo + ", ciudad=" + ciudad + ", usuario=" + usuario
				+ "]";
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombreApellido() {
		return nombreApellido;
	}
	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Tipos getTipo() {
		return tipo;
	}
	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}
	public Ciudad getCiudad() {
		return ciudad;
	}
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Collection<ClasePersonalizada> getClasesPersonalizadas() {
		return clasesPersonalizadas;
	}
	public void setClasesPersonalizadas(Collection<ClasePersonalizada> clasesPersonalizadas) {
		this.clasesPersonalizadas = clasesPersonalizadas;
	}
	public Collection<Inscripcion> getInscripciones() {
		return inscripciones;
	}
	public void setInscripciones(Collection<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}
	public Collection<Cuota> getCuotas() {
		return cuotas;
	}
	public void setCuotas(Collection<Cuota> cuotas) {
		this.cuotas = cuotas;
	}
	public Collection<Asistencia> getAsistencias() {
		return asistencias;
	}
	public void setAsistencias(Collection<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}
	public void agregarClasePersonalizada(ClasePersonalizada clasePersonalizada) {
		clasesPersonalizadas.add(clasePersonalizada);
	}
	public void agregarAsistencia(Asistencia asistencia) {
		asistencias.add(asistencia);
	}
	public void agregarInscripcion(Inscripcion inscripcion) {
		inscripciones.add(inscripcion);
	}
	public void agregarCuota(Cuota cuota) {
		cuotas.add(cuota);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Persona other = (Persona) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
