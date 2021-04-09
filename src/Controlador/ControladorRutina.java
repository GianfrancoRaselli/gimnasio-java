package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorRutina 
{
	public Rutina BuscarUltimaRutinaDelUsuario(Usuario user) throws Exception
	{
		RutinaLogic rl = new RutinaLogic();
		return rl.BuscarUltimaRutinaDelUsuario(user);
	} 
	
	public ArrayList<Rutina> BuscarRutinasDelUsuario(Usuario user) throws Exception
	{
		RutinaLogic rl = new RutinaLogic();
		return (ArrayList<Rutina>) rl.BuscarRutinasDelUsuario(user);
	}
}