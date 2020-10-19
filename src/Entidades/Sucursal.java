package Entidades;

import java.util.Collection;

public class Sucursal 
{
	public Sucursal() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String id;
	private String nombreSucursal;
	private String direccion;
	private Ciudad ciudad;
	private Collection<Horario> horarios;
	private Collection<Asistencia> asistencias;
	private Collection<ClasePersonalizada> clasesPersonalizadas;
	@Override
	public String toString() {
		return "Sucursal [nombreSucursal=" + nombreSucursal + ", direccion=" + direccion + ", ciudad=" + ciudad + "]";
	}
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Ciudad getCiudad() {
		return ciudad;
	}
	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}
	public Collection<Horario> getHorarios() {
		return horarios;
	}
	public void setHorarios(Collection<Horario> horarios) {
		this.horarios = horarios;
	}
	public Collection<Asistencia> getAsistencias() {
		return asistencias;
	}
	public void setAsistencias(Collection<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}
	public Collection<ClasePersonalizada> getClasesPersonalizadas() {
		return clasesPersonalizadas;
	}
	public void setClasesPersonalizadas(Collection<ClasePersonalizada> clasesPersonalizadas) {
		this.clasesPersonalizadas = clasesPersonalizadas;
	}
	public void agregarHorario(Horario horario) {
		horarios.add(horario);
	}
	public void agregarAsistencia(Asistencia asistencia) {
		asistencias.add(asistencia);
	}
	public void agregarClasePersonalizada(ClasePersonalizada clasePersonalizada) {
		clasesPersonalizadas.add(clasePersonalizada);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreSucursal == null) ? 0 : nombreSucursal.hashCode());
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
		Sucursal other = (Sucursal) obj;
		if (nombreSucursal == null) {
			if (other.nombreSucursal != null)
				return false;
		} else if (!nombreSucursal.equals(other.nombreSucursal))
			return false;
		return true;
	}
}
