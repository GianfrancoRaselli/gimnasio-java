package BaseDeDatos;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPool
{	
	private static ConnectionPool dataSource;
	private BasicDataSource basicDataSource = null;
	
	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String host = "localhost";
	private final String port = "3306";
	private final String user = "java";
	private final String password = "java";
	private final String db = "gimnasio";
	private final String timeZone = "serverTimezone=UTC";
	
	private ConnectionPool() 
	{
		basicDataSource = new BasicDataSource();
		
		basicDataSource.setDriverClassName(driver);
		basicDataSource.setUsername(user);
		basicDataSource.setPassword(password);
		basicDataSource.setUrl("jdbc:mysql://"+host+":"+port+"/"+db+"?"+timeZone);
		
		basicDataSource.setMinIdle(3);
		basicDataSource.setMaxIdle(10);;
		basicDataSource.setMaxTotal(30);;
		basicDataSource.setMaxWaitMillis(-1);;
	}
	
	public static ConnectionPool getInstance() 
	{
		if (dataSource == null) 
		{
			dataSource = new ConnectionPool();
		}
		
		return dataSource;
	}
	
	public Connection getConnection() 
	{
		Connection conn = null;
		
		try 
		{
			conn = this.basicDataSource.getConnection();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return conn;
	}
	
	public void closeConnection(Connection conn) 
	{
		try
		{
			conn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
