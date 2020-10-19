package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class HorarioLogic 
{
	public Collection<Horario> BuscarHorarios()
	{
		AdaptadorHorario horarioAdapter = new AdaptadorHorario();
		return horarioAdapter.FindAll();
	}
	
	public Collection<Horario> BuscarPorNombreSucursal(String nombreSucursal)
	{
		AdaptadorHorario horarioAdapter = new AdaptadorHorario();
		return horarioAdapter.BuscarPorNombreSucursal(nombreSucursal);
	}
	
	public boolean EliminarHorario(Horario h)
	{
		AdaptadorHorario horarioAdapter = new AdaptadorHorario();
		return horarioAdapter.Delete(h);
	}
	
	public boolean EditarHorario(Horario h)
	{
		AdaptadorHorario horarioAdapter = new AdaptadorHorario();
		return horarioAdapter.Update(h);
	}
	
	public boolean AgregarHorario(Horario h)
	{
		if(h.getSucursal().getNombreSucursal() != null && h.getDia().getNroDia() != 0 
				&& h.getHoraDesde() != null && h.getHoraHasta() != null)
		{
			AdaptadorHorario horarioAdapter = new AdaptadorHorario();
			return horarioAdapter.Insert(h);
		}
		else
		{
			return false;
		}
	}
}
