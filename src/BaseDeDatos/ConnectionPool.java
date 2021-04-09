package BaseDeDatos;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPool
{	
	private static ConnectionPool dataSource;
	private BasicDataSource basicDataSource = null;
	
	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String host = "localhost";//node59720-gimnasio-java.jelastic.saveincloud.net
	private final String port = "3306";
	private final String user = "java";
	private final String password = "java";//FMTmxp13103
	private final String db = "gimnasio";
	
	private ConnectionPool()
	{
		basicDataSource = new BasicDataSource();
		
		basicDataSource.setDriverClassName(driver);
		basicDataSource.setUsername(user);
		basicDataSource.setPassword(password);
		basicDataSource.setUrl("jdbc:mysql://"+host+":"+port+"/"+db);
		
		basicDataSource.setMinIdle(3);
		basicDataSource.setMaxIdle(10);
		basicDataSource.setMaxTotal(30);
		basicDataSource.setMaxWaitMillis(-1);
	}
	
	public static ConnectionPool getInstance() 
	{
		if (dataSource == null) 
		{
			dataSource = new ConnectionPool();
		}
		
		return dataSource;
	}
	
	public Connection getConnection() throws Exception 
	{
		try 
		{
			return this.basicDataSource.getConnection();
		}
		catch (SQLException e) 
		{
			Exception excepcionManejada = new Exception("Error al abrir la conexión", e);
			throw excepcionManejada;
		}
	}
	
	public void closeConnection(Connection conn) throws Exception 
	{
		try
		{
			if(conn!=null) 
			{
				conn.close();
				conn = null;
			}
		}
		catch(SQLException e)
		{
			Exception excepcionManejada = new Exception("Error al cerrar la conexión", e);
			throw excepcionManejada;
		}
	}
}
