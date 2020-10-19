package Modelo;

import BaseDeDatos.*;
import Entidades.*;

public class AsistenciaLogic 
{
	public boolean AgregarAsistencia(Asistencia a)
	{
		AdaptadorAsistencia asistenciaAdapter = new AdaptadorAsistencia();
		return asistenciaAdapter.Insert(a);
	}
}
