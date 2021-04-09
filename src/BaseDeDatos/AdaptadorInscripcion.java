package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Entidades.*;
import Util.FormateoHora;

public class AdaptadorInscripcion 
{
	public ArrayList<Inscripcion> GetInscriptosEnLaClasePersonalizada(ClasePersonalizada cp) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM inscripciones WHERE fecha_hora_desde=? AND nombre_sucursal=? AND estado=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Inscripcion> inscripciones = new ArrayList<Inscripcion>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, FormateoHora.getFechaHoraEnFormatoBDD(cp.getFechaHoraDesde()));
			statement.setString(2, cp.getSucursal().getNombreSucursal());
			statement.setInt(3, Inscripcion.Estados.Inscripto.getNroEstado());
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				AdaptadorPersona personaAdapter = new AdaptadorPersona();
				
				while(rs.next())
				{
					Inscripcion inscripcion = new Inscripcion();
					Persona p = new Persona();

					p.setDni(rs.getString("dni"));
					inscripcion.setPersona(personaAdapter.GetOne(p));
					switch(rs.getInt("estado"))
					{
						case 1:
							inscripcion.setEstado(Inscripcion.Estados.Inscripto);
							break;
						case 2:
							inscripcion.setEstado(Inscripcion.Estados.Presente);
							break;
						case 3:
							inscripcion.setEstado(Inscripcion.Estados.Ausente);
							break;
						case 4:
							inscripcion.setEstado(Inscripcion.Estados.Cancelada);
							break;
					}
					inscripcion.setFechaHoraInscripcion(rs.getTimestamp("fecha_hora_inscripcion"));
					
					inscripciones.add(inscripcion);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar inscripciones", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return inscripciones;
	}
	
	public ArrayList<Inscripcion> GetInscripcionesDeLaClasePersonalizada(ClasePersonalizada cp) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM inscripciones WHERE fecha_hora_desde=? AND nombre_sucursal=? AND estado IN (?, ?, ?)";
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Inscripcion> inscripciones = new ArrayList<Inscripcion>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, FormateoHora.getFechaHoraEnFormatoBDD(cp.getFechaHoraDesde()));
			statement.setString(2, cp.getSucursal().getNombreSucursal());
			statement.setInt(3, Inscripcion.Estados.Inscripto.getNroEstado());
			statement.setInt(4, Inscripcion.Estados.Presente.getNroEstado());
			statement.setInt(5, Inscripcion.Estados.Ausente.getNroEstado());
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				AdaptadorPersona personaAdapter = new AdaptadorPersona();
				
				while(rs.next())
				{
					Inscripcion inscripcion = new Inscripcion();
					Persona p = new Persona();

					p.setDni(rs.getString("dni"));
					inscripcion.setPersona(personaAdapter.GetOne(p));
					switch(rs.getInt("estado"))
					{
						case 1:
							inscripcion.setEstado(Inscripcion.Estados.Inscripto);
							break;
						case 2:
							inscripcion.setEstado(Inscripcion.Estados.Presente);
							break;
						case 3:
							inscripcion.setEstado(Inscripcion.Estados.Ausente);
							break;
						case 4:
							inscripcion.setEstado(Inscripcion.Estados.Cancelada);
							break;
					}
					inscripcion.setFechaHoraInscripcion(rs.getTimestamp("fecha_hora_inscripcion"));
					
					inscripciones.add(inscripcion);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar inscripciones", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return inscripciones;
	}
	
	public Inscripcion GetUltimaInscripcionDelUsuario(ClasePersonalizada cp, Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM inscripciones WHERE fecha_hora_desde=? AND nombre_sucursal=? "
				+ "AND dni=? AND fecha_hora_inscripcion=(SELECT max(fecha_hora_inscripcion) FROM inscripciones WHERE "
				+ "fecha_hora_desde=? AND nombre_sucursal=? AND dni=?)";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Inscripcion inscripcion = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, FormateoHora.getFechaHoraEnFormatoBDD(cp.getFechaHoraDesde()));
			statement.setString(2, cp.getSucursal().getNombreSucursal());
			statement.setString(3, p.getDni());
			statement.setString(4, FormateoHora.getFechaHoraEnFormatoBDD(cp.getFechaHoraDesde()));
			statement.setString(5, cp.getSucursal().getNombreSucursal());
			statement.setString(6, p.getDni());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				inscripcion = new Inscripcion();

				inscripcion.setPersona(p);
				switch(rs.getInt("estado"))
				{
					case 1:
						inscripcion.setEstado(Inscripcion.Estados.Inscripto);
						break;
					case 2:
						inscripcion.setEstado(Inscripcion.Estados.Presente);
						break;
					case 3:
						inscripcion.setEstado(Inscripcion.Estados.Ausente);
						break;
					case 4:
						inscripcion.setEstado(Inscripcion.Estados.Cancelada);
						break;
				}
				inscripcion.setFechaHoraInscripcion(rs.getTimestamp("fecha_hora_inscripcion"));
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar inscripción", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return inscripcion;
	}
	
	public void Inscribirse(ClasePersonalizada cp, Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO inscripciones (dni, nombre_sucursal, fecha_hora_desde, fecha_hora_inscripcion, estado) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, p.getDni());
			statement.setString(2, cp.getSucursal().getNombreSucursal());
			statement.setString(3, FormateoHora.getFechaHoraEnFormatoBDD(cp.getFechaHoraDesde()));
			statement.setString(4, FormateoHora.getFechaHoraActualEnFormatoBDD());
			statement.setInt(5, Inscripcion.Estados.Inscripto.getNroEstado());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al agregar inscripción", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void DarseDeBaja(ClasePersonalizada cp, Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE inscripciones SET estado=? WHERE BINARY dni=? AND fecha_hora_inscripcion=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, Inscripcion.Estados.Cancelada.getNroEstado());
			statement.setString(2, p.getDni());
			statement.setString(3, FormateoHora.getFechaHoraEnFormatoBDD(cp.getUltimaInscripcionDelUsuario().getFechaHoraInscripcion()));
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al eliminar inscripción", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void RegistrarAsistencias(ClasePersonalizada cp, ArrayList<Persona> inscriptos) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE inscripciones SET estado=? WHERE dni=? AND nombre_sucursal=? AND fecha_hora_desde=? AND estado NOT IN(?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			conn.setAutoCommit(false);
			
			this.RegistrarAusencias(conn, cp);
			
			if(inscriptos != null && !inscriptos.isEmpty())
			{
				for(Persona p: inscriptos)
				{
					statement = conn.prepareStatement(instruccion);
					statement.setInt(1, Inscripcion.Estados.Presente.getNroEstado());
					statement.setString(2, p.getDni());
					statement.setString(3, cp.getSucursal().getNombreSucursal());
					statement.setString(4, cp.getFechaHoraDesdeString());
					statement.setInt(5, Inscripcion.Estados.Cancelada.getNroEstado());
					
					statement.executeUpdate();
				}
			}
			
			conn.commit();
		}
		catch(Exception e)
		{
			conn.rollback();
			
			Exception excepcionManejada = new Exception("Error al registrar asistencia", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void RegistrarAusencias(Connection conn, ClasePersonalizada cp) throws Exception
	{
		String instruccion = "UPDATE inscripciones SET estado=? WHERE nombre_sucursal=? AND fecha_hora_desde=? AND estado NOT IN(?)";
		PreparedStatement statement = null;
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, Inscripcion.Estados.Ausente.getNroEstado());
			statement.setString(2, cp.getSucursal().getNombreSucursal());
			statement.setString(3, cp.getFechaHoraDesdeString());
			statement.setInt(4, Inscripcion.Estados.Cancelada.getNroEstado());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al registrar ausencias", e);
			throw excepcionManejada;
		}
	}
}
