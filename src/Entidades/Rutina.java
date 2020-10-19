package Entidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Rutina 
{
	public Rutina() {
		super();
	}
	private Persona persona;
	private Date fecha;
	private Date hora;
	private String stringFechaHora;
	private Collection<Ejercicio> ejerciciosDia1 = new ArrayList<Ejercicio>();
	private Collection<Ejercicio> ejerciciosDia2 = new ArrayList<Ejercicio>();
	private Collection<Ejercicio> ejerciciosDia3 = new ArrayList<Ejercicio>();
	private Collection<Ejercicio> ejerciciosDia4 = new ArrayList<Ejercicio>();
	private Collection<Ejercicio> ejerciciosDia5 = new ArrayList<Ejercicio>();
	private Collection<Ejercicio> ejerciciosDia6 = new ArrayList<Ejercicio>();
	private Collection<Ejercicio> ejerciciosDia7 = new ArrayList<Ejercicio>();
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public void agregarEjercicioDia1(Ejercicio ejercicio) {
		ejerciciosDia1.add(ejercicio);
	}
	public void agregarEjercicioDia2(Ejercicio ejercicio) {
		ejerciciosDia2.add(ejercicio);
	}
	public void agregarEjercicioDia3(Ejercicio ejercicio) {
		ejerciciosDia3.add(ejercicio);
	}
	public void agregarEjercicioDia4(Ejercicio ejercicio) {
		ejerciciosDia4.add(ejercicio);
	}
	public void agregarEjercicioDia5(Ejercicio ejercicio) {
		ejerciciosDia5.add(ejercicio);
	}
	public void agregarEjercicioDia6(Ejercicio ejercicio) {
		ejerciciosDia6.add(ejercicio);
	}
	public void agregarEjercicioDia7(Ejercicio ejercicio) {
		ejerciciosDia7.add(ejercicio);
	}
	public void eliminarEjercicioDia1(Ejercicio ejercicio) {
		ejerciciosDia1.remove(ejercicio);
		
		ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		int i = 1;
		for(Ejercicio e: ejerciciosDia1)
		{
			e.setOrden(i);
			ejercicios.add(e);
			i++;
		}
		this.setEjerciciosDia1(ejercicios);
	}
	public void eliminarEjercicioDia2(Ejercicio ejercicio) {
		ejerciciosDia2.remove(ejercicio);
		
		ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		int i = 1;
		for(Ejercicio e: ejerciciosDia2)
		{
			e.setOrden(i);
			ejercicios.add(e);
			i++;
		}
		this.setEjerciciosDia2(ejercicios);
	}
	public void eliminarEjercicioDia3(Ejercicio ejercicio) {
		ejerciciosDia3.remove(ejercicio);
		
		ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		int i = 1;
		for(Ejercicio e: ejerciciosDia3)
		{
			e.setOrden(i);
			ejercicios.add(e);
			i++;
		}
		this.setEjerciciosDia3(ejercicios);
	}
	public void eliminarEjercicioDia4(Ejercicio ejercicio) {
		ejerciciosDia4.remove(ejercicio);
		
		ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		int i = 1;
		for(Ejercicio e: ejerciciosDia4)
		{
			e.setOrden(i);
			ejercicios.add(e);
			i++;
		}
		this.setEjerciciosDia4(ejercicios);
	}
	public void eliminarEjercicioDia5(Ejercicio ejercicio) {
		ejerciciosDia5.remove(ejercicio);
		
		ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		int i = 1;
		for(Ejercicio e: ejerciciosDia5)
		{
			e.setOrden(i);
			ejercicios.add(e);
			i++;
		}
		this.setEjerciciosDia5(ejercicios);
	}
	public void eliminarEjercicioDia6(Ejercicio ejercicio) {
		ejerciciosDia6.remove(ejercicio);
		
		ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		int i = 1;
		for(Ejercicio e: ejerciciosDia6)
		{
			e.setOrden(i);
			ejercicios.add(e);
			i++;
		}
		this.setEjerciciosDia6(ejercicios);
	}
	public void eliminarEjercicioDia7(Ejercicio ejercicio) {
		ejerciciosDia7.remove(ejercicio);
		
		ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		int i = 1;
		for(Ejercicio e: ejerciciosDia7)
		{
			e.setOrden(i);
			ejercicios.add(e);
			i++;
		}
		this.setEjerciciosDia7(ejercicios);
	}
	public Collection<Ejercicio> getEjerciciosDia1() {
		return ejerciciosDia1;
	}
	public void setEjerciciosDia1(Collection<Ejercicio> ejerciciosDia1) {
		this.ejerciciosDia1 = ejerciciosDia1;
	}
	public Collection<Ejercicio> getEjerciciosDia2() {
		return ejerciciosDia2;
	}
	public void setEjerciciosDia2(Collection<Ejercicio> ejerciciosDia2) {
		this.ejerciciosDia2 = ejerciciosDia2;
	}
	public Collection<Ejercicio> getEjerciciosDia3() {
		return ejerciciosDia3;
	}
	public void setEjerciciosDia3(Collection<Ejercicio> ejerciciosDia3) {
		this.ejerciciosDia3 = ejerciciosDia3;
	}
	public Collection<Ejercicio> getEjerciciosDia4() {
		return ejerciciosDia4;
	}
	public void setEjerciciosDia4(Collection<Ejercicio> ejerciciosDia4) {
		this.ejerciciosDia4 = ejerciciosDia4;
	}
	public Collection<Ejercicio> getEjerciciosDia5() {
		return ejerciciosDia5;
	}
	public void setEjerciciosDia5(Collection<Ejercicio> ejerciciosDia5) {
		this.ejerciciosDia5 = ejerciciosDia5;
	}
	public Collection<Ejercicio> getEjerciciosDia6() {
		return ejerciciosDia6;
	}
	public void setEjerciciosDia6(Collection<Ejercicio> ejerciciosDia6) {
		this.ejerciciosDia6 = ejerciciosDia6;
	}
	public Collection<Ejercicio> getEjerciciosDia7() {
		return ejerciciosDia7;
	}
	public void setEjerciciosDia7(Collection<Ejercicio> ejerciciosDia7) {
		this.ejerciciosDia7 = ejerciciosDia7;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getHora() {
		return hora;
	}
	public void setHora(Date hora) {
		this.hora = hora;
	}
	@Override
	public String toString() {
		return "Rutina [persona=" + persona + ", fecha=" + fecha + ", hora=" + hora + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
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
		Rutina other = (Rutina) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		return true;
	}
	public String getStringFechaHora() {
		return stringFechaHora;
	}
	public void setStringFechaHora(String stringFechaHora) {
		this.stringFechaHora = stringFechaHora;
	}
}
