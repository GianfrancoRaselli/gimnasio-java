package BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import Entidades.*;

public class AdaptadorNivelUsuario
{
	public NivelUsuario GetOne(NivelUsuario nivel) throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM niveles_usuarios WHERE BINARY nro_nivel=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		connectionPool = ConnectionPool.getInstance();
		conn = connectionPool.getConnection();
		
		try
		{	
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
			Exception excepcionManejada = new Exception("Error al buscar nivel usuario", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return nivel;
	}
	
	public Collection<NivelUsuario> FindAll() throws Exception
	{
		ConnectionPool connectionPool = null;
		Connection conn = null;
		String instruccion = "SELECT * FROM niveles_usuarios";
		PreparedStatement statement = null;
		ResultSet rs = null;
		Collection<NivelUsuario> nivelesUsuarios = new ArrayList<NivelUsuario>();
		
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
					NivelUsuario nivelUsuario = new NivelUsuario();
					
					nivelUsuario.setNroNivel(rs.getInt("nro_nivel"));
					nivelUsuario.setDescripcion(rs.getString("descripcion"));
					
					nivelesUsuarios.add(nivelUsuario);
				}
			}
		}
		catch(Exception e)
		{
			Exception excepcionManejada = new Exception("Error al buscar niveles usuarios", e);
			throw excepcionManejada;
		}
		finally
		{
			if(rs != null) rs.close();
			if(statement != null) statement.close();
			connectionPool.closeConnection(conn);
		}
		
		return nivelesUsuarios;
	}
	
	/*@SuppressWarnings("unchecked")
	public Collection<NivelUsuario> FindAll() throws Exception
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
			Exception excepcionManejada = new Exception("Error al buscar niveles usuarios", e);
			throw excepcionManejada;
		}
		finally
		{
			if(miSesion != null) miSesion.close();
			if(miFactory != null)miFactory.close();
		}
		
		return nivelesUsuarios;
	}*/
}