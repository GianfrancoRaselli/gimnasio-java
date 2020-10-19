package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class AdaptadorNivelUsuario
{
	public NivelUsuario GetOne(NivelUsuario nivel)
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM niveles_usuarios WHERE BINARY nro_nivel=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try
		{
			connectionPool = ConnectionPool.getInstance();
			conn = connectionPool.getConnection();
			
			statement = conn.prepareStatement(instruccion);
			statement.setInt(1, nivel.getNroNivel());
			
			rs = statement.executeQuery();
			
			if(rs!=null && rs.next())
			{
				nivel.setNroNivel(rs.getInt("nro_nivel"));
				nivel.setDescripcion(rs.getString("descripcion"));
			}
			else
			{
				nivel = null;
			}
		}
		catch(Exception e)
		{
			nivel = null;
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
		
		return nivel;
	}
	
	/*public Collection<NivelUsuario> FindAll()
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM niveles_usuarios";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<NivelUsuario> nivelesUsuarios = new ArrayList<NivelUsuario>();
		
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
					NivelUsuario nivel = new NivelUsuario();
					
					nivel.setNroNivel(rs.getInt("nro_nivel"));
					nivel.setDescripcion(rs.getString("descripcion"));
					
					nivelesUsuarios.add(nivel);
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
		
		return nivelesUsuarios;
	}*/
	
	@SuppressWarnings("unchecked")
	public Collection<NivelUsuario> FindAll()
	{
		SessionFactory miFactory = null;
		Session miSesion = null;
		Collection<NivelUsuario> nivelesUsuarios = new ArrayList<NivelUsuario>();
		
		try
		{	
			miFactory = new Configuration().configure("hibernate.org.xml").addAnnotatedClass(NivelUsuario.class).buildSessionFactory();
			miSesion = miFactory.openSession();
			
			miSesion.beginTransaction();
			
			nivelesUsuarios = (ArrayList<NivelUsuario>) miSesion.createQuery("from NivelUsuario").getResultList();
			
			miSesion.getTransaction().commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(miSesion != null) miSesion.close();
			if(miFactory != null)miFactory.close();
		}
		
		return nivelesUsuarios;
	}
}
