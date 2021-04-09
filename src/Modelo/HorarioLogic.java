package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class HorarioLogic 
{
	private AdaptadorHorario horarioAdapter;
	
	public HorarioLogic()
	{
		horarioAdapter = new AdaptadorHorario();
	}
	
	public Collection<Horario> BuscarHorarios() throws Exception
	{
		return horarioAdapter.FindAll();
	}
	
	public Collection<Horario> BuscarPorNombreSucursal(String nombreSucursal) throws Exception
	{
		return horarioAdapter.BuscarPorNombreSucursal(nombreSucursal);
	}
	
	public void EliminarHorario(Horario h) throws Exception
	{
		horarioAdapter.Delete(h);
	}
	
	public void EditarHorario(Horario h) throws Exception
	{
		if(h.getSucursal().getNombreSucursal() != null && h.getDia().getNroDia() != 0 
			&& h.getHoraDesdeString() != null && h.getHoraHastaString() != null
			&& h.getHoraDesdeString().length() == 6 && h.getHoraHastaString().length() == 6
			&& h.getIdHoraDesdeString() != null && h.getIdHoraDesdeString().length() == 6)
		{
			horarioAdapter.Update(h);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
	
	public void AgregarHorario(Horario h) throws Exception
	{
		if(h.getSucursal().getNombreSucursal() != null && h.getDia().getNroDia() != 0 
			&& h.getHoraDesdeString() != null && h.getHoraHastaString() != null
			&& h.getHoraDesdeString().length() == 6 && h.getHoraHastaString().length() == 6)
		{
			horarioAdapter.Insert(h);
		}
		else
		{
			Exception excepcionManejada = new Exception("Error en los datos ingresados");
			throw excepcionManejada;
		}
	}
}
