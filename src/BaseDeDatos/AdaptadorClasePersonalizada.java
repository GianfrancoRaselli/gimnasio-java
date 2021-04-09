package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;
import Util.FormateoHora;

public class AdaptadorClasePersonalizada 
{
	public ArrayList<Float> BuscarPreciosClasesPersonalizadasPorPersona(Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT precio_x_clase FROM inscripciones ins "
				+ "INNER JOIN clases_personalizadas cp ON cp.nombre_sucursal=ins.nombre_sucursal AND cp.fecha_hora_desde=ins.fecha_hora_desde "
				+ "INNER JOIN tipos_clases_personalizadas tcp ON tcp.cod_tipo_clase_personalizada=cp.cod_tipo_clase_personalizada "
				+ "WHERE ins.dni=? AND ins.estado IN(?,?)";
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Float> precios = new ArrayList<Float>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, p.getDni());
			statement.setInt(2, Inscripcion.Estados.Presente.getNroEstado());
			statement.setInt(3, Inscripcion.Estados.Ausente.getNroEstado());
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					precios.add((float) rs.getDouble("precio_x_clase"));
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al recuperar precios de las clases personalizadas", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return precios;
	}
	
	public Collection<ClasePersonalizada> BuscarClasesPersonalizadas(ClasePersonalizada cp, Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = null;
		if(cp.getTipoClasePersonalizada()==null && cp.getSucursal()==null)
		{
			instruccion = "SELECT * FROM clases_personalizadas WHERE year(fecha_hora_desde)=? AND month(fecha_hora_desde)=?";
		}
		else if(cp.getTipoClasePersonalizada()!=null && cp.getSucursal()==null)
		{
			instruccion = "SELECT * FROM clases_personalizadas WHERE year(fecha_hora_desde)=? AND month(fecha_hora_desde)=? "
					+ "AND cod_tipo_clase_personalizada=?";
		}
		else if(cp.getTipoClasePersonalizada()==null && cp.getSucursal()!=null)
		{
			instruccion = "SELECT * FROM clases_personalizadas WHERE year(fecha_hora_desde)=? AND month(fecha_hora_desde)=? "
					+ "AND nombre_sucursal=?";
		}
		else if(cp.getTipoClasePersonalizada()!=null && cp.getSucursal()!=null)
		{
			instruccion = "SELECT * FROM clases_personalizadas WHERE year(fecha_hora_desde)=? AND month(fecha_hora_desde)=? "
					+ "AND cod_tipo_clase_personalizada=? AND nombre_sucursal=?";
		}
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<ClasePersonalizada> clasesPersonalizadas = new ArrayList<ClasePersonalizada>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, cp.getNroAnio());
			statement.setInt(2, cp.getNroMes());
			if(cp.getTipoClasePersonalizada()!=null && cp.getSucursal()==null)
			{
				statement.setInt(3, cp.getTipoClasePersonalizada().getCodTipoClasePersonalizada());
			}
			else if(cp.getTipoClasePersonalizada()==null && cp.getSucursal()!=null)
			{
				statement.setString(3, cp.getSucursal().getNombreSucursal());
			}
			else if(cp.getTipoClasePersonalizada()!=null && cp.getSucursal()!=null)
			{
				statement.setInt(3, cp.getTipoClasePersonalizada().getCodTipoClasePersonalizada());
				statement.setString(4, cp.getSucursal().getNombreSucursal());
			}
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				AdaptadorTipoClasePersonalizada tipoClasePersonalizadaAdapter = new AdaptadorTipoClasePersonalizada();
				AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
				AdaptadorInscripcion inscripcionAdapter = new AdaptadorInscripcion();
				
				while(rs.next())
				{
					ClasePersonalizada clasePersonalizada = new ClasePersonalizada();
					Sucursal sucursal = new Sucursal();
					TipoClasePersonalizada tipoClasePersonalizada = new TipoClasePersonalizada();
					
					clasePersonalizada.setFechaHoraDesde(rs.getTimestamp("fecha_hora_desde"));
					clasePersonalizada.setPeriodoHabilitado(FormateoHora.esMayorQueLaFechaActual(clasePersonalizada.getFechaHoraDesde()));
					clasePersonalizada.setFechaHoraHasta(rs.getTimestamp("fecha_hora_hasta"));
					clasePersonalizada.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
					
					tipoClasePersonalizada.setCodTipoClasePersonalizada(rs.getInt("cod_tipo_clase_personalizada"));
					clasePersonalizada.setTipoClasePersonalizada(tipoClasePersonalizadaAdapter.GetOne(tipoClasePersonalizada));
					
					sucursal.setNombreSucursal(rs.getString("nombre_sucursal"));
					clasePersonalizada.setSucursal(sucursalAdapter.GetOne(sucursal));
					
					clasePersonalizada.setUltimaInscripcionDelUsuario(inscripcionAdapter.GetUltimaInscripcionDelUsuario(clasePersonalizada, p));
					
					if(clasePersonalizada.isPeriodoHabilitado())
					{
						if((clasePersonalizada.getUltimaInscripcionDelUsuario() != null) && (clasePersonalizada.getUltimaInscripcionDelUsuario().getEstado() == Inscripcion.Estados.Inscripto))
						{
							clasePersonalizada.setUsuarioInscripto(true);
						}
						else
						{
							clasePersonalizada.setUsuarioInscripto(false);
						}
					}
					
					clasesPersonalizadas.add(clasePersonalizada);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar clases personalizadas", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return clasesPersonalizadas;
	}
	
	public ClasePersonalizada BuscarClasePersonalizada(ClasePersonalizada cp, Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM clases_personalizadas WHERE fecha_hora_desde=? AND nombre_sucursal=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		ClasePersonalizada clasePersonalizada = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, cp.getFechaHoraDesdeString());
			statement.setString(2, cp.getSucursal().getNombreSucursal());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				AdaptadorTipoClasePersonalizada tipoClasePersonalizadaAdapter = new AdaptadorTipoClasePersonalizada();
				AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
				AdaptadorInscripcion inscripcionAdapter = new AdaptadorInscripcion();
				
				clasePersonalizada = new ClasePersonalizada();
				Sucursal sucursal = new Sucursal();
				TipoClasePersonalizada tipoClasePersonalizada = new TipoClasePersonalizada();
					
				clasePersonalizada.setFechaHoraDesde(rs.getTimestamp("fecha_hora_desde"));
				clasePersonalizada.setPeriodoHabilitado(FormateoHora.esMayorQueLaFechaActual(clasePersonalizada.getFechaHoraDesde()));
				clasePersonalizada.setFechaHoraHasta(rs.getTimestamp("fecha_hora_hasta"));
				clasePersonalizada.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
					
				tipoClasePersonalizada.setCodTipoClasePersonalizada(rs.getInt("cod_tipo_clase_personalizada"));
				clasePersonalizada.setTipoClasePersonalizada(tipoClasePersonalizadaAdapter.GetOne(tipoClasePersonalizada));
					
				sucursal.setNombreSucursal(rs.getString("nombre_sucursal"));
				clasePersonalizada.setSucursal(sucursalAdapter.GetOne(sucursal));
					
				clasePersonalizada.setUltimaInscripcionDelUsuario(inscripcionAdapter.GetUltimaInscripcionDelUsuario(clasePersonalizada, p));
					
				if(clasePersonalizada.isPeriodoHabilitado())
				{
					clasePersonalizada.setInscripciones(inscripcionAdapter.GetInscriptosEnLaClasePersonalizada(clasePersonalizada));
					if(clasePersonalizada.getInscripciones().size() < clasePersonalizada.getCapacidadMaxima())
					{
						clasePersonalizada.setCuposDisponibles(true);
					}
					else
					{
						clasePersonalizada.setCuposDisponibles(false);
					}
						
					if((clasePersonalizada.getUltimaInscripcionDelUsuario() != null) && (clasePersonalizada.getUltimaInscripcionDelUsuario().getEstado() == Inscripcion.Estados.Inscripto))
					{
						clasePersonalizada.setUsuarioInscripto(true);
					}
					else
					{
						clasePersonalizada.setUsuarioInscripto(false);
					}
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar clases personalizadas", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return clasePersonalizada;
	}
	
	public ClasePersonalizada BuscarClasePersonalizada(ClasePersonalizada cp) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM clases_personalizadas WHERE fecha_hora_desde=? AND nombre_sucursal=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		ClasePersonalizada clasePersonalizada = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, cp.getFechaHoraDesdeString());
			statement.setString(2, cp.getSucursal().getNombreSucursal());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				AdaptadorTipoClasePersonalizada tipoClasePersonalizadaAdapter = new AdaptadorTipoClasePersonalizada();
				AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
				AdaptadorInscripcion inscripcionAdapter = new AdaptadorInscripcion();
				
				clasePersonalizada = new ClasePersonalizada();
				Sucursal sucursal = new Sucursal();
				TipoClasePersonalizada tipoClasePersonalizada = new TipoClasePersonalizada();
					
				clasePersonalizada.setFechaHoraDesde(rs.getTimestamp("fecha_hora_desde"));
				clasePersonalizada.setFechaHoraHasta(rs.getTimestamp("fecha_hora_hasta"));
				clasePersonalizada.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
					
				tipoClasePersonalizada.setCodTipoClasePersonalizada(rs.getInt("cod_tipo_clase_personalizada"));
				clasePersonalizada.setTipoClasePersonalizada(tipoClasePersonalizadaAdapter.GetOne(tipoClasePersonalizada));
					
				sucursal.setNombreSucursal(rs.getString("nombre_sucursal"));
				clasePersonalizada.setSucursal(sucursalAdapter.GetOne(sucursal));
					
				clasePersonalizada.setInscripciones(inscripcionAdapter.GetInscripcionesDeLaClasePersonalizada(clasePersonalizada));
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar clase personalizada", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return clasePersonalizada;
	}
	
	public ClasePersonalizada BuscarClasePersonalizadaActual(ClasePersonalizada cp, Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT cp.* FROM clases_personalizadas cp INNER JOIN encargados enc ON enc.nombre_sucursal=cp.nombre_sucursal AND "
				+ "enc.fecha_hora_desde=cp.fecha_hora_desde WHERE ? BETWEEN cp.fecha_hora_desde AND cp.fecha_hora_hasta AND cp.nombre_sucursal=? "
				+ "AND enc.dni=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		ClasePersonalizada clasePersonalizada = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, FormateoHora.getFechaHoraActualEnFormatoBDD());
			statement.setString(2, cp.getSucursal().getNombreSucursal());
			statement.setString(3, p.getDni());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				AdaptadorTipoClasePersonalizada tipoClasePersonalizadaAdapter = new AdaptadorTipoClasePersonalizada();
				AdaptadorSucursal sucursalAdapter = new AdaptadorSucursal();
				AdaptadorInscripcion inscripcionAdapter = new AdaptadorInscripcion();
				
				clasePersonalizada = new ClasePersonalizada();
				Sucursal sucursal = new Sucursal();
				TipoClasePersonalizada tipoClasePersonalizada = new TipoClasePersonalizada();
					
				clasePersonalizada.setFechaHoraDesde(rs.getTimestamp("fecha_hora_desde"));
				clasePersonalizada.setFechaHoraHasta(rs.getTimestamp("fecha_hora_hasta"));
				clasePersonalizada.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
					
				tipoClasePersonalizada.setCodTipoClasePersonalizada(rs.getInt("cod_tipo_clase_personalizada"));
				clasePersonalizada.setTipoClasePersonalizada(tipoClasePersonalizadaAdapter.GetOne(tipoClasePersonalizada));
					
				sucursal.setNombreSucursal(rs.getString("nombre_sucursal"));
				clasePersonalizada.setSucursal(sucursalAdapter.GetOne(sucursal));
					
				clasePersonalizada.setInscripciones(inscripcionAdapter.GetInscripcionesDeLaClasePersonalizada(clasePersonalizada));
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar clase personalizada", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return clasePersonalizada;
	}
}