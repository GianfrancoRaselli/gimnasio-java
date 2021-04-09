package Controlador;

import java.util.ArrayList;

import Entidades.*;
import Modelo.*;

public class ControladorHorario 
{
	public ArrayList<Horario> BuscarHorarios() throws Exception
	{
		HorarioLogic hl = new HorarioLogic();
		return (ArrayList<Horario>) hl.BuscarHorarios();
	} 
}