package Modelo;

import BaseDeDatos.*;
import Entidades.*;

public class AsistenciaLogic 
{
	private AdaptadorAsistencia asistenciaAdapter;
	
	public AsistenciaLogic()
	{
		asistenciaAdapter = new AdaptadorAsistencia();
	}
	
	public void AgregarAsistencia(Asistencia a) throws Exception
	{
		asistenciaAdapter.Insert(a);
	}
}
