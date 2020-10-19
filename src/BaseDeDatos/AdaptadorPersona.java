package BaseDeDatos;

import java.sql.*;
import java.util.*;
import Entidades.*;


public class AdaptadorPersona
{
	public Persona GetOne(Persona p)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM personas WHERE BINARY dni=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
			p = null;
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
		
		return p;
	}
	
	public Collection<Persona> BuscarPersonasDeudoras(int anio, int mes)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT DISTINCT dni FROM asistencias WHERE YEAR(fecha_hora)=? AND MONTH(fecha_hora)=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Persona> personas = new ArrayList<Persona>();
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
		
		return personas;
	}
	
	public Collection<Persona> FindAll()
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM personas ORDER BY nombre_apellido";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<Persona> personas = new ArrayList<Persona>();
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
		
		return personas;
	}
	
	public boolean Delete(Persona p)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "DELETE FROM personas WHERE BINARY dni=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setString(1, p.getDni());
			
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
	
	public boolean Insert(Persona p)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "INSERT INTO personas (dni, nombre_apellido, telefono, email, direccion, tipo, cod_postal) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
	
	public boolean Update(Persona p)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE personas SET dni=?, nombre_apellido=?, telefono=?, email=?, direccion=?, tipo=?, cod_postal=? WHERE dni=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
	
	public boolean UpdatePerfil(Persona p)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "UPDATE personas SET nombre_apellido=?, telefono=?, email=?, direccion=?, cod_postal=? WHERE dni=?";
		PreparedStatement statement = null;
		boolean devolucion = true;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
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
}
