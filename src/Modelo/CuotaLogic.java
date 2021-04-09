package Modelo;

import java.util.ArrayList;
import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;
import Util.FormateoHora;

public class CuotaLogic 
{
	private AdaptadorCuota cuotaAdapter;
	
	public CuotaLogic()
	{
		cuotaAdapter = new AdaptadorCuota();
	}
	
	public void ActualizarCuotas() throws Exception
	{
		ActualizacionCuotas ac = cuotaAdapter.BuscarUltimaActualizacion();
		
		if(ac != null)
		{
			int anioActual = Integer.parseInt(FormateoHora.getAnioActual());
			int mesActual = Integer.parseInt(FormateoHora.getMesActual());
	
			if(!((ac.getAnio() == anioActual && (ac.getMes()+1) == mesActual) || (ac.getMes() == 12 && (ac.getAnio()+1) == anioActual && (ac.getMes()+1) == 1)))
			{
				AdaptadorPersona personaAdapter = new AdaptadorPersona();
				AdaptadorClasePersonalizada clasePersonalizadaAdapter = new AdaptadorClasePersonalizada();
				AdaptadorPrecioGimnasio precioGimnasioAdapter = new AdaptadorPrecioGimnasio();
				
				PrecioGimnasio pg = precioGimnasioAdapter.GetPrecioActual();
				
				if(pg != null)
				{
					this.IncrementarUnMes(ac);
					
					while(ac.getAnio() != anioActual || ac.getMes() != mesActual)
					{
						ArrayList<Persona> personasDeudoras = (ArrayList<Persona>) personaAdapter.BuscarPersonasDeudoras(ac.getAnio(), ac.getMes());
						
						if(!personasDeudoras.isEmpty())
						{
							ArrayList<Cuota> cuotas = new ArrayList<Cuota>();
									
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
								
								cuotas.add(cuota);
							}
							
							cuotaAdapter.InsertCuotas(cuotas);
						}
						
						cuotaAdapter.InsertActualizacionCuotas(ac);
						
						this.IncrementarUnMes(ac);
					}
				}
				else
				{
					Exception excepcionManejada = new Exception("No se encontró precio del gimnasio");
					throw excepcionManejada;
				}
			}
		}
		else
		{
			Exception excepcionManejada = new Exception("No se encontró última actualización de cuotas");
			throw excepcionManejada;
		}
	}
	
	public void IncrementarUnMes(ActualizacionCuotas ac)
	{
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
	
	public Collection<Cuota> BuscarCuotasImpagas(Persona p) throws Exception
	{
		return cuotaAdapter.BuscarCuotasImpagas(p);
	}
	
	public Collection<Cuota> BuscarCuotasDePersona(Persona p) throws Exception
	{
		return cuotaAdapter.BuscarCuotasDePersona(p);
	}
	
	public void PagarCuota(Cuota c) throws Exception
	{
		cuotaAdapter.PagarCuota(c);
	}
}