package Modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import BaseDeDatos.*;
import Entidades.*;

public class CuotaLogic 
{
	public void ActualizarCuotas()
	{
		AdaptadorCuota cuotaAdapter = new AdaptadorCuota();
		
		ActualizacionCuotas ac = cuotaAdapter.BuscarUltimaActualizacion();
		
		if(ac != null)
		{
			int mesActual = new Date().getMonth() + 1;
			int anioActual = new Date().getYear() + 1900;
	
			if(!((ac.getAnio() == anioActual && (ac.getMes()+1) == mesActual) || (ac.getMes() == 12 && (ac.getAnio()+1) == anioActual && (ac.getMes()+1) == 1)))
			{
				AdaptadorPersona personaAdapter = new AdaptadorPersona();
				AdaptadorClasePersonalizada clasePersonalizadaAdapter = new AdaptadorClasePersonalizada();
				AdaptadorPrecioGimnasio precioAdapter = new AdaptadorPrecioGimnasio();
				
				PrecioGimnasio pg = precioAdapter.GetPrecioActual();
				
				if(pg != null)
				{
					while(ac.getAnio() != anioActual || ac.getMes() != mesActual)
					{
						ArrayList<Persona> personasDeudoras = (ArrayList<Persona>) personaAdapter.BuscarPersonasDeudoras(ac.getAnio(), ac.getMes());
						
						if(!personasDeudoras.isEmpty())
						{
							for(Persona p: personasDeudoras)
							{
								ArrayList<Float> precios = clasePersonalizadaAdapter.BuscarPreciosClasesPersonalizadasPorPersona(p);
								float precioExtraTotal = 0;
								
								if(!precios.isEmpty())
								{
									for(float precio: precios)
									{
										precioExtraTotal = precioExtraTotal + precio;
									}
								}
								
								Cuota cuota = new Cuota();					
								cuota.setPersona(p);
								cuota.setAnio(ac.getAnio());
								cuota.setMes(ac.getMes());
								cuota.setTotal(pg.getPrecio() + precioExtraTotal);
								
								cuotaAdapter.Insert(cuota);
							}
						}
						
						cuotaAdapter.InsertActualizacionCuotas(ac);
						
						if((ac.getMes()+1) != 13)
						{
							ac.setAnio(ac.getAnio());
							ac.setMes(ac.getMes() + 1);
						}
						else
						{
							ac.setAnio(ac.getAnio() + 1);
							ac.setMes(1);
						}
					}
				}
			}
		}
	}
	
	public Collection<Cuota> BuscarCuotasImpagas(Persona p)
	{
		AdaptadorCuota cuotaAdapter = new AdaptadorCuota();
		return cuotaAdapter.BuscarCuotasImpagas(p);
	}
	
	public Collection<Cuota> BuscarCuotasDePersona(Persona p)
	{
		AdaptadorCuota cuotaAdapter = new AdaptadorCuota();
		return cuotaAdapter.BuscarCuotasDePersona(p);
	}
	
	public boolean PagarCuota(Cuota c)
	{
		AdaptadorCuota cuotaAdapter = new AdaptadorCuota();
		return cuotaAdapter.PagarCuota(c);
	}
}
