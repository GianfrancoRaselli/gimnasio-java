package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorPersona 
{
	public ArrayList<Persona> BuscarPersonas() throws Exception
	{
		PersonaLogic pl = new PersonaLogic();
		return (ArrayList<Persona>) pl.BuscarPersonas();
	}
}