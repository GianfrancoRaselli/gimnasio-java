package Modelo;

import java.util.ArrayList;

import BaseDeDatos.*;
import Entidades.*;

public class InscripcionLogic 
{
	private AdaptadorInscripcion inscripcionAdapter;
	
	public InscripcionLogic()
	{
		inscripcionAdapter = new AdaptadorInscripcion();
	}
	
	public void Inscribirse(ClasePersonalizada cp, Persona p) throws Exception
	{
		inscripcionAdapter.Inscribirse(cp, p);
	}
	
	public void DarseDeBaja(ClasePersonalizada cp, Persona p) throws Exception
	{
		inscripcionAdapter.DarseDeBaja(cp, p);
	}
	
	public void RegistrarAsistencias(ClasePersonalizada cp, ArrayList<Persona> inscriptos) throws Exception
	{
		inscripcionAdapter.RegistrarAsistencias(cp, inscriptos);
	}
}