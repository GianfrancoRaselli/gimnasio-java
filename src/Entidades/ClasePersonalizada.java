package Entidades;

import java.sql.Timestamp;
import java.util.Collection;

public class ClasePersonalizada 
{
	public ClasePersonalizada() {
		super();
	}
	private TipoClasePersonalizada tipoClasePersonalizada;
	private Sucursal sucursal;
	private Timestamp fechaHoraDesde;
	private Timestamp fechaHoraHasta;
	private String fechaHoraDesdeString;
	private String fechaHoraHastaString;
	private int nroAnio;
	private int nroMes;
	private int capacidadMaxima;
	private boolean cuposDisponibles;
	private boolean periodoHabilitado;
	private boolean usuarioInscripto;
	private Inscripcion ultimaInscripcionDelUsuario;
	private Collection<Inscripcion> inscripciones;
	private Collection<Persona> entrenadores;
	public TipoClasePersonalizada getTipoClasePersonalizada() {
		return tipoClasePersonalizada;
	}
	public void setTipoClasePersonalizada(TipoClasePersonalizada tipoClasePersonalizada) {
		this.tipoClasePersonalizada = tipoClasePersonalizada;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public Timestamp getFechaHoraDesde() {
		return fechaHoraDesde;
	}
	public void setFechaHoraDesde(Timestamp fechaHoraDesde) {
		this.fechaHoraDesde = fechaHoraDesde;
	}
	public Timestamp getFechaHoraHasta() {
		return fechaHoraHasta;
	}
	public void setFechaHoraHasta(Timestamp fechaHoraHasta) {
		this.fechaHoraHasta = fechaHoraHasta;
	}
	public int getCapacidadMaxima() {
		return capacidadMaxima;
	}
	public void setCapacidadMaxima(int capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}
	public Collection<Inscripcion> getInscripciones() {
		return inscripciones;
	}
	public void setInscripciones(Collection<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}
	public Collection<Persona> getEntrenadores() {
		return entrenadores;
	}
	public void setEntrenadores(Collection<Persona> entrenadores) {
		this.entrenadores = entrenadores;
	}
	public void agregarInscripcion(Inscripcion inscripcion) {
		inscripciones.add(inscripcion);
	}
	public void agregarEntrenador(Persona entrenador) {
		entrenadores.add(entrenador);
	}
	@Override
	public String toString() {
		return "ClasePersonalizada [tipoClasePersonalizada=" + tipoClasePersonalizada + ", sucursal=" + sucursal
				+ ", fechaHoraDesde=" + fechaHoraDesde + ", fechaHoraHasta=" + fechaHoraHasta + ", capacidadMaxima="
				+ capacidadMaxima + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaHoraDesde == null) ? 0 : fechaHoraDesde.hashCode());
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
		ClasePersonalizada other = (ClasePersonalizada) obj;
		if (fechaHoraDesde == null) {
			if (other.fechaHoraDesde != null)
				return false;
		} else if (!fechaHoraDesde.equals(other.fechaHoraDesde))
			return false;
		if (sucursal == null) {
			if (other.sucursal != null)
				return false;
		} else if (!sucursal.equals(other.sucursal))
			return false;
		return true;
	}
	public boolean isCuposDisponibles() {
		return cuposDisponibles;
	}
	public void setCuposDisponibles(boolean cuposDisponibles) {
		this.cuposDisponibles = cuposDisponibles;
	}
	public boolean isPeriodoHabilitado() {
		return periodoHabilitado;
	}
	public void setPeriodoHabilitado(boolean periodoHabilitado) {
		this.periodoHabilitado = periodoHabilitado;
	}
	public boolean isUsuarioInscripto() {
		return usuarioInscripto;
	}
	public void setUsuarioInscripto(boolean usuarioInscripto) {
		this.usuarioInscripto = usuarioInscripto;
	}
	public Inscripcion getUltimaInscripcionDelUsuario() {
		return ultimaInscripcionDelUsuario;
	}
	public void setUltimaInscripcionDelUsuario(Inscripcion ultimaInscripcionDelUsuario) {
		this.ultimaInscripcionDelUsuario = ultimaInscripcionDelUsuario;
	}
	public String getFechaHoraDesdeString() {
		return fechaHoraDesdeString;
	}
	public void setFechaHoraDesdeString(String fechaHoraDesdeString) {
		this.fechaHoraDesdeString = fechaHoraDesdeString;
	}
	public String getFechaHoraHastaString() {
		return fechaHoraHastaString;
	}
	public void setFechaHoraHastaString(String fechaHoraHastaString) {
		this.fechaHoraHastaString = fechaHoraHastaString;
	}
	public int getNroAnio() {
		return nroAnio;
	}
	public void setNroAnio(int nroAnio) {
		this.nroAnio = nroAnio;
	}
	public int getNroMes() {
		return nroMes;
	}
	public void setNroMes(int nroMes) {
		this.nroMes = nroMes;
	}
}
