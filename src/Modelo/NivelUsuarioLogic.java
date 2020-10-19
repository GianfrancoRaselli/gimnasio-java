package Modelo;

import java.util.Collection;

import BaseDeDatos.*;
import Entidades.*;

public class NivelUsuarioLogic 
{
	public Collection<NivelUsuario> BuscarNivelesUsuarios()
	{
		AdaptadorNivelUsuario nivelUsuarioAdapter = new AdaptadorNivelUsuario();
		return nivelUsuarioAdapter.FindAll();
	}
}
