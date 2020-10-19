package Entidades;

public class Ejercicio 
{
	public enum Tipos{
		Repeticion(1), Tiempo(2);
		
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
	
	public enum NroDias{
		Primero(1), Segundo(2), Tercero(3), Cuarto(4), Quinto(5), Sexto(6), Septimo(7);
		
		private int nroDia;
		
		private NroDias(int nroDia)
		{
			this.nroDia = nroDia;
		}
		
		public int getNroDia()
		{
			return this.nroDia;
		}
	}
	
	public Ejercicio() {
		super();
	}
	private Rutina rutina;
	private TipoEjercicio tipoEjercicio;
	private NroDias nroDia;
	private int orden;
	private Tipos tipo;
	private String tiempo;
	private String series;
	private String repeticiones;
	private String pesos;
	public Rutina getRutina() {
		return rutina;
	}
	public void setRutina(Rutina rutina) {
		this.rutina = rutina;
	}
	public TipoEjercicio getTipoEjercicio() {
		return tipoEjercicio;
	}
	public void setTipoEjercicio(TipoEjercicio tipoEjercicio) {
		this.tipoEjercicio = tipoEjercicio;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public Tipos getTipo() {
		return tipo;
	}
	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getRepeticiones() {
		return repeticiones;
	}
	public void setRepeticiones(String repeticiones) {
		this.repeticiones = repeticiones;
	}
	public String getPesos() {
		return pesos;
	}
	public void setPesos(String pesos) {
		this.pesos = pesos;
	}
	public NroDias getNroDia() {
		return nroDia;
	}
	public void setNroDia(NroDias nroDia) {
		this.nroDia = nroDia;
	}
	@Override
	public String toString() {
		return "Ejercicio [rutina=" + rutina + ", tipoEjercicio=" + tipoEjercicio + ", nroDia=" + nroDia + ", orden="
				+ orden + ", tipo=" + tipo + ", tiempo=" + tiempo + ", series=" + series + ", repeticiones="
				+ repeticiones + ", pesos=" + pesos + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nroDia == null) ? 0 : nroDia.hashCode());
		result = prime * result + orden;
		result = prime * result + ((rutina == null) ? 0 : rutina.hashCode());
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
		Ejercicio other = (Ejercicio) obj;
		if (nroDia != other.nroDia)
			return false;
		if (orden != other.orden)
			return false;
		if (rutina == null) {
			if (other.rutina != null)
				return false;
		} else if (!rutina.equals(other.rutina))
			return false;
		return true;
	}
}
