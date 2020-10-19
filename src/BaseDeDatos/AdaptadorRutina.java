package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import Entidades.*;

public class AdaptadorRutina 
{
	public Rutina BuscarUltimaRutinaDelUsuario(Usuario userSesion)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT dni, fecha_hora FROM rutinas WHERE dni=? "
				+ "EXCEPT (SELECT r.dni, r.fecha_hora FROM rutinas r, rutinas ru "
				+ "WHERE r.fecha_hora<ru.fecha_hora AND r.dni=ru.dni AND r.dni=?)";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Rutina r = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
				
				r.setFecha(rs.getDate("fecha_hora"));
				r.setHora(rs.getTime("fecha_hora"));
				
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
			r = null;
			e.printStackTrace();
		}
		finally
		{
			try
			{
				try 
				{
					if(rs != null) rs.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					if(statement != null) statement.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
				connectionPool.closeConnection(conn);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return r;
	}
	
	public Rutina BuscarRutina(Rutina r)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT dni, fecha_hora FROM rutinas WHERE dni=? AND fecha_hora=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, r.getPersona().getDni());
			statement.setString(2, r.getStringFechaHora());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				r = new Rutina();
				Persona p = new Persona();
				
				p.setDni(rs.getString("dni"));
				r.setPersona(p);
				
				r.setFecha(rs.getDate("fecha_hora"));
				r.setHora(rs.getTime("fecha_hora"));
				
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
			r = null;
			e.printStackTrace();
		}
		finally
		{
			try
			{
				try 
				{
					if(rs != null) rs.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					if(statement != null) statement.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
				connectionPool.closeConnection(conn);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return r;
	}
	
	public boolean Insert(Rutina r)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO rutinas (dni, fecha_hora) VALUES (?, ?)";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, r.getPersona().getDni());

			String anio = String.valueOf(r.getFecha().getYear()+1900); 
			
			String mes;
			if(r.getFecha().getMonth()+1 < 10)
			{
				mes = "0" + String.valueOf(r.getFecha().getMonth()+1);
			}
			else
			{
				mes = String.valueOf(r.getFecha().getMonth()+1);
			}
			
			String dia;
			if(r.getFecha().getDate() < 10)
			{
				dia = "0" + String.valueOf(r.getFecha().getDate());
			}
			else
			{
				dia = String.valueOf(r.getFecha().getDate());
			}
			
			String hora;
			if(r.getFecha().getHours() < 10)
			{
				hora = "0" + String.valueOf(r.getFecha().getHours());
			}
			else
			{
				hora = String.valueOf(r.getFecha().getHours());
			}
			
			String min;
			if(r.getFecha().getMinutes() < 10)
			{
				min = "0" + String.valueOf(r.getFecha().getMinutes());
			}
			else
			{
				min = String.valueOf(r.getFecha().getMinutes());
			}
			
			String seg;
			if(r.getFecha().getSeconds() < 10)
			{
				seg = "0" + String.valueOf(r.getFecha().getSeconds());
			}
			else
			{
				seg = String.valueOf(r.getFecha().getSeconds());
			}
			
			statement.setString(2, anio + mes + dia + hora + min + seg);
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			devolucion = false;
			e.printStackTrace();
		}
		finally
		{
			try
			{
				try 
				{
					if(statement != null) statement.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
				connectionPool.closeConnection(conn);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return devolucion;
	}
	
	public boolean InsertEjercicios(Rutina r)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO ejercicios (dni, fecha_hora, nro_dia, orden, cod_tipo_ejercicio, tipo, "
				+ "series, repeticiones, pesos, tiempo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = null;
		ArrayList<Ejercicio> ejercicios = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
			
						String anio = String.valueOf(r.getFecha().getYear()+1900); 
						
						String mes;
						if(r.getFecha().getMonth()+1 < 10)
						{
							mes = "0" + String.valueOf(r.getFecha().getMonth()+1);
						}
						else
						{
							mes = String.valueOf(r.getFecha().getMonth()+1);
						}
						
						String dia;
						if(r.getFecha().getDate() < 10)
						{
							dia = "0" + String.valueOf(r.getFecha().getDate());
						}
						else
						{
							dia = String.valueOf(r.getFecha().getDate());
						}
						
						String hora;
						if(r.getFecha().getHours() < 10)
						{
							hora = "0" + String.valueOf(r.getFecha().getHours());
						}
						else
						{
							hora = String.valueOf(r.getFecha().getHours());
						}
						
						String min;
						if(r.getFecha().getMinutes() < 10)
						{
							min = "0" + String.valueOf(r.getFecha().getMinutes());
						}
						else
						{
							min = String.valueOf(r.getFecha().getMinutes());
						}
						
						String seg;
						if(r.getFecha().getSeconds() < 10)
						{
							seg = "0" + String.valueOf(r.getFecha().getSeconds());
						}
						else
						{
							seg = String.valueOf(r.getFecha().getSeconds());
						}
						
						statement.setString(2, anio + mes + dia + hora + min + seg);
						
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
			devolucion = false;
			e.printStackTrace();
		}
		finally
		{
			try
			{
				try 
				{
					if(statement != null) statement.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
				connectionPool.closeConnection(conn);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return devolucion;
	}
	
	public Collection<Ejercicio> BuscarEjercicios(Rutina r, int nroDia)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM ejercicios WHERE dni=? AND fecha_hora=? AND nro_dia=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, r.getPersona().getDni());

			String anio = String.valueOf(r.getFecha().getYear()+1900); 
			
			String mes;
			if(r.getFecha().getMonth()+1 < 10)
			{
				mes = "0" + String.valueOf(r.getFecha().getMonth()+1);
			}
			else
			{
				mes = String.valueOf(r.getFecha().getMonth()+1);
			}
			
			String dia;
			if(r.getFecha().getDate() < 10)
			{
				dia = "0" + String.valueOf(r.getFecha().getDate());
			}
			else
			{
				dia = String.valueOf(r.getFecha().getDate());
			}
			
			String hora;
			if(r.getHora().getHours()+3 < 10)
			{
				hora = "0" + String.valueOf(r.getHora().getHours()+3);
			}
			else
			{
				hora = String.valueOf(r.getHora().getHours()+3);
			}
			
			String min;
			if(r.getHora().getMinutes() < 10)
			{
				min = "0" + String.valueOf(r.getHora().getMinutes());
			}
			else
			{
				min = String.valueOf(r.getHora().getMinutes());
			}
			
			String seg;
			if(r.getHora().getSeconds() < 10)
			{
				seg = "0" + String.valueOf(r.getHora().getSeconds());
			}
			else
			{
				seg = String.valueOf(r.getHora().getSeconds());
			}
			
			statement.setString(2, anio + mes + dia + hora + min + seg);			
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
						default:
							e.setTipo(null);
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
						default:
							e.setNroDia(null);
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
			e.printStackTrace();
		}
		finally
		{
			try
			{
				try 
				{
					if(rs != null) rs.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					if(statement != null) statement.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
				connectionPool.closeConnection(conn);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ejercicios;
	}
	
	public Collection<Rutina> BuscarRutinasDelUsuario(Usuario userSesion)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM rutinas WHERE dni=? ORDER BY fecha_hora DESC";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Rutina> rutinas = new ArrayList<Rutina>();
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, userSesion.getPersona().getDni());
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					Rutina r = new Rutina();
					
					r.setFecha(rs.getDate("fecha_hora"));
					r.setHora(rs.getTime("fecha_hora"));
					
					rutinas.add(r);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				try 
				{
					if(rs != null) rs.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				try 
				{
					if(statement != null) statement.close();
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
				connectionPool.closeConnection(conn);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return rutinas;
	}
}