package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class NivelUsuarioLogic 
{
	private AdaptadorNivelUsuario nivelUsuarioAdapter;
	
	public NivelUsuarioLogic()
	{
		nivelUsuarioAdapter = new AdaptadorNivelUsuario();
	}
	
	public Collection<NivelUsuario> BuscarNivelesUsuarios() throws Exception
	{
		return nivelUsuarioAdapter.FindAll();
	}
}