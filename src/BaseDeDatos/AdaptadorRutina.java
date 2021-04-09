package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;
import Util.FormateoHora;

public class AdaptadorRutina 
{
	public Rutina BuscarUltimaRutinaDelUsuario(Usuario userSesion) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT dni, fecha_hora FROM rutinas WHERE dni=? "
				+ "EXCEPT (SELECT r.dni, r.fecha_hora FROM rutinas r, rutinas ru "
				+ "WHERE r.fecha_hora<ru.fecha_hora AND r.dni=ru.dni AND r.dni=?)";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Rutina r = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, userSesion.getPersona().getDni());
			statement.setString(2, userSesion.getPersona().getDni());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				r = new Rutina();
				Persona p = new Persona();
				p.setDni(rs.getString("dni"));
				r.setPersona(p);
				
				r.setFechaHora(rs.getTimestamp("fecha_hora"));
				
				for(int i=1; i<=7; i++)
				{
					switch(i)
					{
						case 1:
							r.setEjerciciosDia1(this.BuscarEjercicios(r, i));
							break;
						case 2:
							r.setEjerciciosDia2(this.BuscarEjercicios(r, i));
							break;
						case 3:
							r.setEjerciciosDia3(this.BuscarEjercicios(r, i));
							break;
						case 4:
							r.setEjerciciosDia4(this.BuscarEjercicios(r, i));
							break;
						case 5:
							r.setEjerciciosDia5(this.BuscarEjercicios(r, i));
							break;
						case 6:
							r.setEjerciciosDia6(this.BuscarEjercicios(r, i));
							break;
						case 7:
							r.setEjerciciosDia7(this.BuscarEjercicios(r, i));
							break;
					}
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar última rutina", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return r;
	}
	
	public Collection<Rutina> BuscarRutinasDelUsuario(Usuario userSesion) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM rutinas WHERE dni=? ORDER BY fecha_hora DESC";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Rutina> rutinas = new ArrayList<Rutina>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, userSesion.getPersona().getDni());
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					Rutina r = new Rutina();
					
					r.setFechaHora(rs.getTimestamp("fecha_hora"));
					
					rutinas.add(r);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar rutinas del usuario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return rutinas;
	}
	
	public Rutina BuscarRutina(Rutina r) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT dni, fecha_hora FROM rutinas WHERE dni=? AND fecha_hora=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, r.getPersona().getDni());
			statement.setString(2, r.getFechaHoraString());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				r = new Rutina();
				Persona p = new Persona();
				
				p.setDni(rs.getString("dni"));
				r.setPersona(p);
				
				r.setFechaHora(rs.getTimestamp("fecha_hora"));
				
				for(int i=1; i<=7; i++)
				{
					switch(i)
					{
						case 1:
							r.setEjerciciosDia1(this.BuscarEjercicios(r, i));
							break;
						case 2:
							r.setEjerciciosDia2(this.BuscarEjercicios(r, i));
							break;
						case 3:
							r.setEjerciciosDia3(this.BuscarEjercicios(r, i));
							break;
						case 4:
							r.setEjerciciosDia4(this.BuscarEjercicios(r, i));
							break;
						case 5:
							r.setEjerciciosDia5(this.BuscarEjercicios(r, i));
							break;
						case 6:
							r.setEjerciciosDia6(this.BuscarEjercicios(r, i));
							break;
						case 7:
							r.setEjerciciosDia7(this.BuscarEjercicios(r, i));
							break;
					}
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar rutina", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return r;
	}
	
	public Collection<Ejercicio> BuscarEjercicios(Rutina r, int nroDia) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM ejercicios WHERE dni=? AND fecha_hora=? AND nro_dia=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, r.getPersona().getDni());
			statement.setString(2, FormateoHora.getFechaHoraEnFormatoBDD(r.getFechaHora()));
			statement.setInt(3, nroDia);
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					Ejercicio e = new Ejercicio();
					
					AdaptadorTipoEjercicio tipoEjercicioAdapter = new AdaptadorTipoEjercicio();
					TipoEjercicio te = new TipoEjercicio();
					te.setCodTipoEjercicio(rs.getInt("cod_tipo_ejercicio"));
					e.setTipoEjercicio(tipoEjercicioAdapter.GetOne(te));
					
					switch(rs.getInt("tipo"))
					{
						case 1:
							e.setTipo(Ejercicio.Tipos.Repeticion);
							break;
						case 2:
							e.setTipo(Ejercicio.Tipos.Tiempo);
							break;
					}
					
					switch(rs.getInt("nro_dia"))
					{
						case 1:
							e.setNroDia(Ejercicio.NroDias.Primero);
							break;
						case 2:
							e.setNroDia(Ejercicio.NroDias.Segundo);
							break;
						case 3:
							e.setNroDia(Ejercicio.NroDias.Tercero);
							break;
						case 4:
							e.setNroDia(Ejercicio.NroDias.Cuarto);
							break;
						case 5:
							e.setNroDia(Ejercicio.NroDias.Quinto);
							break;
						case 6:
							e.setNroDia(Ejercicio.NroDias.Sexto);
							break;
						case 7:
							e.setNroDia(Ejercicio.NroDias.Septimo);
							break;
					}
					
					e.setOrden(rs.getInt("orden"));
					e.setSeries(rs.getString("series"));
					e.setRepeticiones(rs.getString("repeticiones"));
					e.setPesos(rs.getString("pesos"));
					e.setTiempo(rs.getString("tiempo"));
					
					ejercicios.add(e);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar ejercicios", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return ejercicios;
	}
	
	public void Insert(Rutina r) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO rutinas (dni, fecha_hora) VALUES (?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			conn.setAutoCommit(false);
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, r.getPersona().getDni());
			statement.setString(2, r.getFechaHoraString());
			
			statement.executeUpdate();
			
			this.InsertEjercicios(conn, r);
			
			conn.commit();
		}
		catch(Exception e)
		{
			conn.rollback();
			
			Exception excepcionManejada = new Exception("Error al agregar rutina", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void InsertEjercicios(Connection conn, Rutina r) throws Exception
	{
		String instruccion = "INSERT INTO ejercicios (dni, fecha_hora, nro_dia, orden, cod_tipo_ejercicio, tipo, "
				+ "series, repeticiones, pesos, tiempo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = null;
		ArrayList<Ejercicio> ejercicios = null;
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			
			for(int i=0; i<=7; i++)
			{
				switch(i)
				{
					case 1:
						ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia1();
						break;
					case 2:
						ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia2();
						break;
					case 3:
						ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia3();
						break;
					case 4:
						ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia4();
						break;
					case 5:
						ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia5();
						break;
					case 6:
						ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia6();
						break;
					case 7:
						ejercicios = (ArrayList<Ejercicio>) r.getEjerciciosDia7();
						break;
				}
				
				if(ejercicios!=null && !ejercicios.isEmpty())
				{
					for(Ejercicio ejercicio: ejercicios)
					{
						statement.setString(1, r.getPersona().getDni());
						statement.setString(2, r.getFechaHoraString());
						
						switch(ejercicio.getNroDia())
						{
							case Primero:
								statement.setInt(3, 1);
								break;
							case Segundo:
								statement.setInt(3, 2);
								break;
							case Tercero:
								statement.setInt(3, 3);
								break;
							case Cuarto:
								statement.setInt(3, 4);
								break;
							case Quinto:
								statement.setInt(3, 5);
								break;
							case Sexto:
								statement.setInt(3, 6);
								break;
							case Septimo:
								statement.setInt(3, 7);
								break;
						}

						statement.setInt(4, ejercicio.getOrden());
						statement.setInt(5, ejercicio.getTipoEjercicio().getCodTipoEjercicio());
						
						switch(ejercicio.getTipo())
						{
							case Repeticion:
								statement.setInt(6, 1);
								break;
							case Tiempo:
								statement.setInt(6, 2);
								break;
						}

						statement.setString(7, ejercicio.getSeries());
						statement.setString(8, ejercicio.getRepeticiones());
						statement.setString(9, ejercicio.getPesos());
						statement.setString(10, ejercicio.getTiempo());
						
						statement.executeUpdate();
					}
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al agregar ejercicios", e);
			throw excepcionManejada;
		}
	}
}