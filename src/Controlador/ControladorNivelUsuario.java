package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorNivelUsuario 
{
	public ArrayList<NivelUsuario> BuscarNivelesUsuarios() throws Exception
	{
		NivelUsuarioLogic nul = new NivelUsuarioLogic();
		return (ArrayList<NivelUsuario>) nul.BuscarNivelesUsuarios();
	} 
}