package BaseDeDatos;

import java.sql.*;
import java.util.*;
import Entidades.*;


public class AdaptadorPersona
{
	public Persona GetOne(Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM personas WHERE BINARY dni=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, p.getDni());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				Ciudad ciudad = new Ciudad();
				ciudad.setCodPostal(rs.getString("cod_postal"));
				
				p.setDni(rs.getString("dni"));
				p.setNombreApellido(rs.getString("nombre_apellido"));
				p.setTelefono(rs.getString("telefono"));
				p.setEmail(rs.getString("email"));
				p.setDireccion(rs.getString("direccion"));
				switch(rs.getInt("tipo"))
				{
					case 1:
						p.setTipo(Persona.Tipos.Administrativo);
						break;
					case 2:
						p.setTipo(Persona.Tipos.Entrenador);
						break;
					case 3:
						p.setTipo(Persona.Tipos.Cliente);
						break;
				}
				p.setCiudad(ciudad);
			}
			else
			{
				p = null;
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar persona", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return p;
	}
	
	public Collection<Persona> BuscarPersonasDeudoras(int anio, int mes) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT DISTINCT dni FROM asistencias WHERE YEAR(fecha_hora)=? AND MONTH(fecha_hora)=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Persona> personas = new ArrayList<Persona>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, anio);
			statement.setInt(2, mes);
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					Persona p = new Persona();
					p.setDni(rs.getString("dni"));
					personas.add(p);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar personas deudoras", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return personas;
	}
	
	public Collection<Persona> FindAll() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM personas ORDER BY nombre_apellido";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Persona> personas = new ArrayList<Persona>();
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			
			rs = statement.executeQuery();
			
			if(rs!=null)
			{
				while(rs.next())
				{
					Persona p = new Persona();
					Ciudad ciudad = new Ciudad();
					
					p.setDni(rs.getString("dni"));
					p.setNombreApellido(rs.getString("nombre_apellido"));
					p.setTelefono(rs.getString("telefono"));
					p.setEmail(rs.getString("email"));
					p.setDireccion(rs.getString("direccion"));
					switch(rs.getInt("tipo"))
					{
						case 1:
							p.setTipo(Persona.Tipos.Administrativo);
							break;
						case 2:
							p.setTipo(Persona.Tipos.Entrenador);
							break;
						case 3:
							p.setTipo(Persona.Tipos.Cliente);
							break;
					}
					ciudad.setCodPostal(rs.getString("cod_postal"));
					p.setCiudad(ciudad);
					
					personas.add(p);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar personas", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return personas;
	}
	
	public void Delete(Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "DELETE FROM personas WHERE BINARY dni=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, p.getDni());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al eliminar persona", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void Insert(Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO personas (dni, nombre_apellido, telefono, email, direccion, tipo, cod_postal) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, p.getDni());
			statement.setString(2, p.getNombreApellido());
			statement.setString(3, p.getTelefono());
			statement.setString(4, p.getEmail());
			statement.setString(5, p.getDireccion());
			statement.setInt(6, p.getTipo().getNroTipo());
			statement.setString(7, p.getCiudad().getCodPostal());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al agregar persona", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void Update(Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE personas SET dni=?, nombre_apellido=?, telefono=?, email=?, direccion=?, tipo=?, cod_postal=? WHERE dni=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, p.getDni());
			statement.setString(2, p.getNombreApellido());
			statement.setString(3, p.getTelefono());
			statement.setString(4, p.getEmail());
			statement.setString(5, p.getDireccion());
			statement.setInt(6, p.getTipo().getNroTipo());
			statement.setString(7, p.getCiudad().getCodPostal());
			statement.setString(8, p.getId());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al actualizar persona", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
	
	public void UpdatePerfil(Persona p) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE personas SET nombre_apellido=?, telefono=?, email=?, direccion=?, cod_postal=? WHERE dni=?";
		PreparedStatement statement = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
			statement = conn.prepareStatement(instruccion);;
			statement.setString(1, p.getNombreApellido());
			statement.setString(2, p.getTelefono());
			statement.setString(3, p.getEmail());
			statement.setString(4, p.getDireccion());
			statement.setString(5, p.getCiudad().getCodPostal());
			statement.setString(6, p.getDni());
			
			statement.executeUpdate();
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al actualizar perfil", e);
			throw excepcionManejada;
		}
		finally
		{
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
	}
}