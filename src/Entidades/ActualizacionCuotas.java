package Entidades;

public class ActualizacionCuotas 
{
	public ActualizacionCuotas() {
		super();
	}
	private int anio;
	private int mes;
	@Override
	public String toString() {
		return "ActualizacionCuota [anio=" + anio + ", mes=" + mes + "]";
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anio;
		result = prime * result + mes;
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
		ActualizacionCuotas other = (ActualizacionCuotas) obj;
		if (anio != other.anio)
			return false;
		if (mes != other.mes)
			return false;
		return true;
	}
}
